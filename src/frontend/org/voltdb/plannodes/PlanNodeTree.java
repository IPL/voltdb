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

package org.voltdb.plannodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json_voltpatches.JSONArray;
import org.json_voltpatches.JSONException;
import org.json_voltpatches.JSONObject;
import org.json_voltpatches.JSONString;
import org.json_voltpatches.JSONStringer;
import org.voltcore.utils.Pair;
import org.voltdb.VoltType;
import org.voltdb.catalog.Database;
import org.voltdb.expressions.AbstractExpression;
import org.voltdb.expressions.SubqueryExpression;
import org.voltdb.types.ExpressionType;
import org.voltdb.types.PlanNodeType;

/**
 *
 */
public class PlanNodeTree implements JSONString {

    public enum Members {
        PLAN_NODES,
        PLAN_NODES_LISTS,
        PARAMETERS,
        PARAMETER_IDX;
    }

    protected final List< Pair< Integer, VoltType > > m_parameters = new ArrayList< Pair< Integer, VoltType > >();
    // Subquery ID / subquery plan node list map. The top level statement always has id = 0
    protected final List<List<AbstractPlanNode>> m_planNodesList = new ArrayList<List<AbstractPlanNode>>();
    // Subquery ID / subquery root node plan
    protected final Map<Integer, AbstractPlanNode> m_subqueryMap = new HashMap<Integer, AbstractPlanNode>();

    public PlanNodeTree() {
    }

    public PlanNodeTree(AbstractPlanNode root_node) {
        try {
            List<AbstractPlanNode> nodeList = new ArrayList<AbstractPlanNode>();
            m_planNodesList.add(nodeList);
            constructTree(nodeList, root_node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getRootPlanNodeId() {
        assert(!m_planNodesList.isEmpty() && !m_planNodesList.get(0).isEmpty());
        return m_planNodesList.get(0).get(0).getPlanNodeId();
    }

    public AbstractPlanNode getRootPlanNode() {
        assert(!m_planNodesList.isEmpty() && !m_planNodesList.get(0).isEmpty());
        return m_planNodesList.get(0).get(0);
    }

    public Boolean constructTree(List<AbstractPlanNode> planNodes, AbstractPlanNode node) throws Exception {
        planNodes.add(node);
        extractSubqueries(node);
        for (int i = 0; i < node.getChildCount(); i++) {
            AbstractPlanNode child = node.getChild(i);
            if (!constructTree(planNodes, child)) {
                return false;
            }
        }
        return true;
    }

    public List< Pair< Integer, VoltType > > getParameters() {
        return m_parameters;
    }

    public void setParameters(List< Pair< Integer, VoltType > > parameters) {
        m_parameters.clear();
        m_parameters.addAll(parameters);
    }

    @Override
    public String toJSONString() {
        JSONStringer stringer = new JSONStringer();
        try {
            stringer.object();
            toJSONString(stringer);
            stringer.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return stringer.toString();
    }

    public void toJSONString(JSONStringer stringer) throws JSONException {
        if (m_planNodesList.size() == 1) {
            stringer.key(Members.PLAN_NODES.name()).array();
            for (AbstractPlanNode node : m_planNodesList.get(0)) {
                assert (node instanceof JSONString);
                stringer.value(node);
            }
            stringer.endArray(); //end entries
        } else {
            stringer.key(Members.PLAN_NODES_LISTS.name()).array();
            for (List<AbstractPlanNode> planNodes : m_planNodesList) {
                stringer.object().key(Members.PLAN_NODES.name()).array();
                for (AbstractPlanNode node : planNodes) {
                    assert (node instanceof JSONString);
                    stringer.value(node);
                }
                stringer.endArray().endObject(); //end entries
            }
            stringer.endArray(); //end entries
        }

        if (m_parameters.size() > 0) {
            stringer.key(Members.PARAMETERS.name()).array();
            for (Pair< Integer, VoltType > parameter : m_parameters) {
                stringer.array().value(parameter.getFirst()).value(parameter.getSecond().name()).endArray();
            }
            stringer.endArray();
        }
    }

    public List<AbstractPlanNode> getNodeList() {
        return m_planNodesList.get(0);
    }

    public List<AbstractPlanNode> getNodeList(int idx) {
        assert(idx < m_planNodesList.size());
        return m_planNodesList.get(idx);
    }

    /**
     *  Load json plan. The plan must have either "PLAN_NODE" array in case of a statement without
     *  subqueries or "PLAN_NODES_LISTS" array of "PLAN_NODE" arrays for each sub statement.
     * @param jobj
     * @param db
     * @throws JSONException
     */
    public void loadFromJSONPlan( JSONObject jobj, Database db )  throws JSONException {
        if (jobj.has(PlanNodeTree.Members.PLAN_NODES_LISTS.name())) {
            JSONArray jplanNodesArray = jobj.getJSONArray(PlanNodeTree.Members.PLAN_NODES_LISTS.name());
            for (int i = 0; i < jplanNodesArray.length(); ++i) {
                JSONObject jplanNodesObj = jplanNodesArray.getJSONObject(i);
                JSONArray jplanNodes = jplanNodesObj.getJSONArray(PlanNodeTree.Members.PLAN_NODES.name());
                loadPlanNodesFromJSONArrays(jplanNodes, db);
            }
        } else {
            JSONArray jplanNodes = jobj.getJSONArray(PlanNodeTree.Members.PLAN_NODES.name());
            loadPlanNodesFromJSONArrays(jplanNodes, db);
        }
        // Connect the parent and child statements
        for (List<AbstractPlanNode> nextPlanNodes : m_planNodesList) {
            connectParentChildStmt(nextPlanNodes);
        }
    }

    /**
     *  Load plan nodes from the "PLAN_NODE" array
     * @param jArray - PLAN_NODES
     * @param db
     * @throws JSONException
     */

    public void loadPlanNodesFromJSONArrays( JSONArray jArray, Database db )  {
        List<AbstractPlanNode> planNodes = new ArrayList<AbstractPlanNode>();
        m_planNodesList.add(planNodes);
        loadFromJSONArray(jArray, db, planNodes);
    }

    private void loadFromJSONArray( JSONArray jArray, Database db, List<AbstractPlanNode> planNodes)  {
        int size = jArray.length();

        try {
            for( int i = 0; i < size; i++ ) {
                JSONObject jobj;
                jobj = jArray.getJSONObject(i);
                String nodeTypeStr = jobj.getString("PLAN_NODE_TYPE");
                PlanNodeType nodeType = PlanNodeType.get( nodeTypeStr );
                AbstractPlanNode apn = null;
                try {
                    apn = nodeType.getPlanNodeClass().newInstance();
                } catch (InstantiationException e) {
                    System.err.println( e.getMessage() );
                    e.printStackTrace();
                    return;
                } catch (IllegalAccessException e) {
                    System.err.println( e.getMessage() );
                    e.printStackTrace();
                    return;
                }
                apn.loadFromJSONObject(jobj, db);
                planNodes.add(apn);
            }
            //link children and parents
            for( int i = 0; i < size; i++ ) {
                JSONObject jobj;
                jobj = jArray.getJSONObject(i);
                if (jobj.has("CHILDREN_IDS")) {
                    JSONArray children = jobj.getJSONArray("CHILDREN_IDS");
                    for( int j = 0; j < children.length(); j++ ) {
                        planNodes.get(i).addAndLinkChild( getNodeofId( children.getInt(j), planNodes ) );
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void connectParentChildStmt(List<AbstractPlanNode> planNodes) {
        for(AbstractPlanNode node : planNodes) {
            if (node instanceof AbstractScanPlanNode) {
                AbstractScanPlanNode scanNode = (AbstractScanPlanNode)node;
                connectPredicateStmt(scanNode.getPredicate());
            } else if (node instanceof AbstractJoinPlanNode) {
                AbstractJoinPlanNode joinNode = (AbstractJoinPlanNode) node;
                connectPredicateStmt(joinNode.getPreJoinPredicate());
                connectPredicateStmt(joinNode.getJoinPredicate());
                connectPredicateStmt(joinNode.getWherePredicate());
            }
        }
    }

    private void connectPredicateStmt(AbstractExpression predicate) {
        if (predicate == null) {
            return;
        }
        List<AbstractExpression> existsExprs = predicate.findAllSubexpressionsOfType(
                ExpressionType.OPERATOR_EXISTS);
        for (AbstractExpression expr : existsExprs) {
            assert(expr.getLeft() != null && expr.getLeft() instanceof SubqueryExpression);
            SubqueryExpression subqueryExpr = (SubqueryExpression) expr.getLeft();
            int subqueryId = subqueryExpr.getSubqueryId();
            int subqueryNodeId = subqueryExpr.getSubqueryNodeId();
            List<AbstractPlanNode> subqueryNodes = m_planNodesList.get(subqueryId);
            AbstractPlanNode subqueryNode = getNodeofId(subqueryNodeId, subqueryNodes);
            assert(subqueryNode != null);
            subqueryExpr.setSubqueryNode(subqueryNode);
        }
    }

    public AbstractPlanNode getNodeofId (int ID, List<AbstractPlanNode> planNodes) {
        int size = planNodes.size();
        for( int i = 0; i < size; i++ ) {
            if( planNodes.get(i).getPlanNodeId() == ID ) {
                return planNodes.get(i);
            }
        }
        return null;
    }

    /**
     * Traverse down the plan extracting all the subquery plans. The potential places where
     * the suqueries could be found are:
     *  - NestLoopInPlan
     *  - AbstractScanPlanNode predicate
     *  - AbstractJoinPlanNode predicates
     * @param node
     * @throws Exception
     */
    private void extractSubqueries(AbstractPlanNode node)  throws Exception {
        if (node instanceof AbstractScanPlanNode) {
            AbstractScanPlanNode scanNode = (AbstractScanPlanNode) node;
            extractSubqueriesFromExpression(scanNode.getPredicate());
        } else if (node instanceof AbstractJoinPlanNode) {
            AbstractJoinPlanNode joinNode = (AbstractJoinPlanNode) node;
            extractSubqueriesFromExpression(joinNode.getPreJoinPredicate());
            extractSubqueriesFromExpression(joinNode.getJoinPredicate());
            extractSubqueriesFromExpression(joinNode.getWherePredicate());
            AbstractPlanNode inlineScan = joinNode.getInlinePlanNode(PlanNodeType.INDEXSCAN);
            if (inlineScan != null) {
                assert(inlineScan instanceof AbstractScanPlanNode);
                extractSubqueriesFromExpression(((AbstractScanPlanNode)inlineScan).getPredicate());
            }
        }
    }
    private void extractSubqueriesFromExpression(AbstractExpression expr)  throws Exception {
        if (expr == null) {
            return;
        }
        List<AbstractExpression> subexprs = expr.findAllSubexpressionsOfType(ExpressionType.SUBQUERY);
        for(AbstractExpression nextexpr : subexprs) {
            assert(nextexpr instanceof SubqueryExpression);
            SubqueryExpression subqueryExpr = (SubqueryExpression) nextexpr;
            int stmtId = subqueryExpr.getSubqueryId();
            m_subqueryMap.put(stmtId, subqueryExpr.getSubqueryNode());
            List<AbstractPlanNode> planNodes = new ArrayList<AbstractPlanNode>();
            assert(stmtId == m_planNodesList.size());
            m_planNodesList.add(planNodes);
            constructTree(planNodes, subqueryExpr.getSubqueryNode());
        }
    }

}
