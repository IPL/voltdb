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
 * Information used to build and update a materialized view
 */
public class MaterializedViewInfo extends CatalogType {

    CatalogMap<ColumnRef> m_groupbycols;
    String m_predicate = new String();
    String m_groupbyExpressionsJson = new String();
    String m_aggregationExpressionsJson = new String();
    String m_indexForMinMax = new String();

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("dest", null);
        m_groupbycols = new CatalogMap<ColumnRef>(catalog, this, path + "/" + "groupbycols", ColumnRef.class);
        m_childCollections.put("groupbycols", m_groupbycols);
        m_fields.put("predicate", m_predicate);
        m_fields.put("groupbyExpressionsJson", m_groupbyExpressionsJson);
        m_fields.put("aggregationExpressionsJson", m_aggregationExpressionsJson);
        m_fields.put("indexForMinMax", m_indexForMinMax);
    }

    void update() {
        m_predicate = (String) m_fields.get("predicate");
        m_groupbyExpressionsJson = (String) m_fields.get("groupbyExpressionsJson");
        m_aggregationExpressionsJson = (String) m_fields.get("aggregationExpressionsJson");
        m_indexForMinMax = (String) m_fields.get("indexForMinMax");
    }

    /** GETTER: The table which will be updated when the source table is updated */
    public Table getDest() {
        Object o = getField("dest");
        if (o instanceof UnresolvedInfo) {
            UnresolvedInfo ui = (UnresolvedInfo) o;
            Table retval = (Table) m_catalog.getItemForRef(ui.path);
            assert(retval != null);
            m_fields.put("dest", retval);
            return retval;
        }
        return (Table) o;
    }

    /** GETTER: The columns involved in the group by of the aggregation */
    public CatalogMap<ColumnRef> getGroupbycols() {
        return m_groupbycols;
    }

    /** GETTER: A filtering predicate */
    public String getPredicate() {
        return m_predicate;
    }

    /** GETTER: A serialized representation of the groupby expression trees */
    public String getGroupbyexpressionsjson() {
        return m_groupbyExpressionsJson;
    }

    /** GETTER: A serialized representation of the aggregation expression trees */
    public String getAggregationexpressionsjson() {
        return m_aggregationExpressionsJson;
    }

    /** GETTER: The name of index on srcTable which can be used to maintain min()/max() */
    public String getIndexforminmax() {
        return m_indexForMinMax;
    }

    /** SETTER: The table which will be updated when the source table is updated */
    public void setDest(Table value) {
        m_fields.put("dest", value);
    }

    /** SETTER: A filtering predicate */
    public void setPredicate(String value) {
        m_predicate = value; m_fields.put("predicate", value);
    }

    /** SETTER: A serialized representation of the groupby expression trees */
    public void setGroupbyexpressionsjson(String value) {
        m_groupbyExpressionsJson = value; m_fields.put("groupbyExpressionsJson", value);
    }

    /** SETTER: A serialized representation of the aggregation expression trees */
    public void setAggregationexpressionsjson(String value) {
        m_aggregationExpressionsJson = value; m_fields.put("aggregationExpressionsJson", value);
    }

    /** SETTER: The name of index on srcTable which can be used to maintain min()/max() */
    public void setIndexforminmax(String value) {
        m_indexForMinMax = value; m_fields.put("indexForMinMax", value);
    }

}
