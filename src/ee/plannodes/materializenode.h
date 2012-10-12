/* This file is part of VoltDB.
 * Copyright (C) 2008-2012 VoltDB Inc.
 *
 * This file contains original code and/or modifications of original code.
 * Any modifications made by VoltDB Inc. are licensed under the following
 * terms and conditions:
 *
 * VoltDB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VoltDB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */
/* Copyright (C) 2008 by H-Store Project
 * Brown University
 * Massachusetts Institute of Technology
 * Yale University
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

#ifndef HSTOREMATERIALIZENODE_H
#define HSTOREMATERIALIZENODE_H

#include "projectionnode.h"
#include "expressions/abstractexpression.h"

namespace voltdb {

class Table;

/**
 * A node to convert parameters into one or more tuples (such as new tuples for an insert).
 */
class MaterializePlanNode : public ProjectionPlanNode {
    // The following comment describes an idea that was NOT implemented in VoltDB:
    // --paul 10/10/2012
    // A MaterializePlanNode is sort of like a special case ProjectionPlanNode where
    // we can auto-generate any primary keys that we may need. For the initial system, this
    // doesn't mean anything because we are going to require that all tables have a pkey. In
    // the future, however, we may need to be able to handle tables without pkeys or tables
    // that want the database to pick a unique pkey value for each new tuple (similiar to a sequence
    // or auto-increment in other DBMSes). So the Materialize operation would be able to do
    // something special to populate these columns
    // Andy - 06/25/2008
    //
public:
    MaterializePlanNode() { /* Do Nothing */ }
    virtual PlanNodeType getPlanNodeType() const { return (PLAN_NODE_TYPE_MATERIALIZE); }

    std::string debugInfo(const std::string &spacer) const;
private:
    virtual void loadFromJSONObject(json_spirit::Object &obj);
};

}

#endif
