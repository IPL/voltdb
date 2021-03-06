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

/* WARNING: THIS FILE IS AUTO-GENERATED
            DO NOT MODIFY THIS SOURCE
            ALL CHANGES MUST BE MADE IN THE CATALOG GENERATOR */

package org.voltdb.catalog;

/**
 * A parameterized SQL statement embedded in a stored procedure
 */
public class Statement extends CatalogType {

    String m_sqltext = new String();
    int m_querytype;
    boolean m_readonly;
    boolean m_singlepartition;
    boolean m_replicatedtabledml;
    boolean m_iscontentdeterministic;
    boolean m_isorderdeterministic;
    String m_nondeterminismdetail = new String();
    boolean m_batched;
    int m_paramnum;
    CatalogMap<StmtParameter> m_parameters;
    CatalogMap<PlanFragment> m_fragments;
    int m_cost;
    int m_seqscancount;
    String m_explainplan = new String();

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("sqltext", m_sqltext);
        m_fields.put("querytype", m_querytype);
        m_fields.put("readonly", m_readonly);
        m_fields.put("singlepartition", m_singlepartition);
        m_fields.put("replicatedtabledml", m_replicatedtabledml);
        m_fields.put("iscontentdeterministic", m_iscontentdeterministic);
        m_fields.put("isorderdeterministic", m_isorderdeterministic);
        m_fields.put("nondeterminismdetail", m_nondeterminismdetail);
        m_fields.put("batched", m_batched);
        m_fields.put("paramnum", m_paramnum);
        m_parameters = new CatalogMap<StmtParameter>(catalog, this, path + "/" + "parameters", StmtParameter.class);
        m_childCollections.put("parameters", m_parameters);
        m_fragments = new CatalogMap<PlanFragment>(catalog, this, path + "/" + "fragments", PlanFragment.class);
        m_childCollections.put("fragments", m_fragments);
        m_fields.put("cost", m_cost);
        m_fields.put("seqscancount", m_seqscancount);
        m_fields.put("explainplan", m_explainplan);
    }

    void update() {
        m_sqltext = (String) m_fields.get("sqltext");
        m_querytype = (Integer) m_fields.get("querytype");
        m_readonly = (Boolean) m_fields.get("readonly");
        m_singlepartition = (Boolean) m_fields.get("singlepartition");
        m_replicatedtabledml = (Boolean) m_fields.get("replicatedtabledml");
        m_iscontentdeterministic = (Boolean) m_fields.get("iscontentdeterministic");
        m_isorderdeterministic = (Boolean) m_fields.get("isorderdeterministic");
        m_nondeterminismdetail = (String) m_fields.get("nondeterminismdetail");
        m_batched = (Boolean) m_fields.get("batched");
        m_paramnum = (Integer) m_fields.get("paramnum");
        m_cost = (Integer) m_fields.get("cost");
        m_seqscancount = (Integer) m_fields.get("seqscancount");
        m_explainplan = (String) m_fields.get("explainplan");
    }

    /** GETTER: The text of the sql statement */
    public String getSqltext() {
        return m_sqltext;
    }

    public int getQuerytype() {
        return m_querytype;
    }

    /** GETTER: Can the statement modify any data? */
    public boolean getReadonly() {
        return m_readonly;
    }

    /** GETTER: Does the statement only use data on one partition? */
    public boolean getSinglepartition() {
        return m_singlepartition;
    }

    /** GETTER: Should the result of this statememt be divided by partition count before returned */
    public boolean getReplicatedtabledml() {
        return m_replicatedtabledml;
    }

    /** GETTER: Is the result of this statement deterministic not accounting for row order */
    public boolean getIscontentdeterministic() {
        return m_iscontentdeterministic;
    }

    /** GETTER: Is the result of this statement deterministic even accounting for row order */
    public boolean getIsorderdeterministic() {
        return m_isorderdeterministic;
    }

    /** GETTER: Explanation for any non-determinism in the statement result */
    public String getNondeterminismdetail() {
        return m_nondeterminismdetail;
    }

    public boolean getBatched() {
        return m_batched;
    }

    public int getParamnum() {
        return m_paramnum;
    }

    /** GETTER: The set of parameters to this SQL statement */
    public CatalogMap<StmtParameter> getParameters() {
        return m_parameters;
    }

    /** GETTER: The set of plan fragments used to execute this statement */
    public CatalogMap<PlanFragment> getFragments() {
        return m_fragments;
    }

    /** GETTER: The cost of this plan measured in arbitrary units */
    public int getCost() {
        return m_cost;
    }

    /** GETTER: The number of sequential table scans in the plan */
    public int getSeqscancount() {
        return m_seqscancount;
    }

    /** GETTER: A human-readable plan description */
    public String getExplainplan() {
        return m_explainplan;
    }

    /** SETTER: The text of the sql statement */
    public void setSqltext(String value) {
        m_sqltext = value; m_fields.put("sqltext", value);
    }

    public void setQuerytype(int value) {
        m_querytype = value; m_fields.put("querytype", value);
    }

    /** SETTER: Can the statement modify any data? */
    public void setReadonly(boolean value) {
        m_readonly = value; m_fields.put("readonly", value);
    }

    /** SETTER: Does the statement only use data on one partition? */
    public void setSinglepartition(boolean value) {
        m_singlepartition = value; m_fields.put("singlepartition", value);
    }

    /** SETTER: Should the result of this statememt be divided by partition count before returned */
    public void setReplicatedtabledml(boolean value) {
        m_replicatedtabledml = value; m_fields.put("replicatedtabledml", value);
    }

    /** SETTER: Is the result of this statement deterministic not accounting for row order */
    public void setIscontentdeterministic(boolean value) {
        m_iscontentdeterministic = value; m_fields.put("iscontentdeterministic", value);
    }

    /** SETTER: Is the result of this statement deterministic even accounting for row order */
    public void setIsorderdeterministic(boolean value) {
        m_isorderdeterministic = value; m_fields.put("isorderdeterministic", value);
    }

    /** SETTER: Explanation for any non-determinism in the statement result */
    public void setNondeterminismdetail(String value) {
        m_nondeterminismdetail = value; m_fields.put("nondeterminismdetail", value);
    }

    public void setBatched(boolean value) {
        m_batched = value; m_fields.put("batched", value);
    }

    public void setParamnum(int value) {
        m_paramnum = value; m_fields.put("paramnum", value);
    }

    /** SETTER: The cost of this plan measured in arbitrary units */
    public void setCost(int value) {
        m_cost = value; m_fields.put("cost", value);
    }

    /** SETTER: The number of sequential table scans in the plan */
    public void setSeqscancount(int value) {
        m_seqscancount = value; m_fields.put("seqscancount", value);
    }

    /** SETTER: A human-readable plan description */
    public void setExplainplan(String value) {
        m_explainplan = value; m_fields.put("explainplan", value);
    }

}
