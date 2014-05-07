/* This file is part of VoltDB.
 * Copyright (C) 2008-2014 VoltDB Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

#include "subqueryexpression.h"

#include "common/debuglog.h"
#include "common/executorcontext.hpp"
#include "common/NValue.hpp"
#include "executors/executorutil.h"

namespace voltdb {

    SubqueryExpression::SubqueryExpression(int subqueryId,
        const std::vector<int>& paramIdxs,
        const std::vector<AbstractExpression*>* tveParams) :
            AbstractExpression(EXPRESSION_TYPE_SUBQUERY),
            m_subqueryId(subqueryId), m_paramIdxs(paramIdxs),
            m_tveParams(tveParams),
            m_parameterContainer(NULL)
    {
        VOLT_TRACE("SubqueryExpression %d", subqueryId);
        m_parameterContainer = &ExecutorContext::getExecutorContext()->getParameterContainer();
        assert((m_tveParams.get() == NULL && m_paramIdxs.empty()) ||
            (m_tveParams.get() != NULL && m_paramIdxs.size() == m_tveParams->size()));
    }

    SubqueryExpression::~SubqueryExpression() {
        if (m_tveParams.get() != NULL) {
            size_t i = m_tveParams->size();
            while (i--) {
                delete (*m_tveParams)[i];
            }
        }
    }

    NValue
    SubqueryExpression::eval(const TableTuple *tuple1, const TableTuple *tuple2) const
    {
        std::vector<AbstractExecutor*>* executionStack =
            &ExecutorContext::getExecutorContext()->getExecutorList(m_subqueryId);
        assert(executionStack != NULL);
        assert(!executionStack->empty());
        VOLT_TRACE ("Running subquery: %d", m_subqueryId);
        // Substitute parameters
        if (m_tveParams.get() != NULL) {
            size_t paramsCnt = m_tveParams->size();
            for (int i = 0; i < paramsCnt; ++i) {
                AbstractExpression* tveParam = (*m_tveParams)[i];
                NValue param = tveParam->eval(tuple1, tuple2);
                (*m_parameterContainer)[m_paramIdxs[i]] = param;
            }
        }
        // Run the executors
        int status = executeExecutionVector(*executionStack, *m_parameterContainer);
        if (status != ENGINE_ERRORCODE_SUCCESS) {
            char message[256];
            snprintf(message, 256, "Failed to execute the subquery '%d'", m_subqueryId);
            throw SerializableEEException(VOLT_EE_EXCEPTION_TYPE_EEEXCEPTION, message);
        }
        bool result = executionStack->back()->getPlanNode()->getOutputTable()->activeTupleCount() != 0;
        return (result) ? NValue::getTrue() : NValue::getFalse();
    }

}

