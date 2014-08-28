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
 * A index structure on a database table's columns
 */
public class Index extends CatalogType {

    boolean m_unique;
    boolean m_assumeUnique;
    boolean m_countable;
    int m_type;
    CatalogMap<ColumnRef> m_columns;
    String m_expressionsjson = new String();

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("unique", m_unique);
        m_fields.put("assumeUnique", m_assumeUnique);
        m_fields.put("countable", m_countable);
        m_fields.put("type", m_type);
        m_columns = new CatalogMap<ColumnRef>(catalog, this, path + "/" + "columns", ColumnRef.class);
        m_childCollections.put("columns", m_columns);
        m_fields.put("expressionsjson", m_expressionsjson);
    }

    void update() {
        m_unique = (Boolean) m_fields.get("unique");
        m_assumeUnique = (Boolean) m_fields.get("assumeUnique");
        m_countable = (Boolean) m_fields.get("countable");
        m_type = (Integer) m_fields.get("type");
        m_expressionsjson = (String) m_fields.get("expressionsjson");
    }

    /** GETTER: May the index contain duplicate keys? */
    public boolean getUnique() {
        return m_unique;
    }

    /** GETTER: User allow unique index on partition table without including partition key? */
    public boolean getAssumeunique() {
        return m_assumeUnique;
    }

    /** GETTER: Index counter feature */
    public boolean getCountable() {
        return m_countable;
    }

    /** GETTER: What data structure is the index using and what kinds of keys does it support? */
    public int getType() {
        return m_type;
    }

    /** GETTER: Columns referenced by the index */
    public CatalogMap<ColumnRef> getColumns() {
        return m_columns;
    }

    /** GETTER: A serialized representation of the optional expression trees */
    public String getExpressionsjson() {
        return m_expressionsjson;
    }

    /** SETTER: May the index contain duplicate keys? */
    public void setUnique(boolean value) {
        m_unique = value; m_fields.put("unique", value);
    }

    /** SETTER: User allow unique index on partition table without including partition key? */
    public void setAssumeunique(boolean value) {
        m_assumeUnique = value; m_fields.put("assumeUnique", value);
    }

    /** SETTER: Index counter feature */
    public void setCountable(boolean value) {
        m_countable = value; m_fields.put("countable", value);
    }

    /** SETTER: What data structure is the index using and what kinds of keys does it support? */
    public void setType(int value) {
        m_type = value; m_fields.put("type", value);
    }

    /** SETTER: A serialized representation of the optional expression trees */
    public void setExpressionsjson(String value) {
        m_expressionsjson = value; m_fields.put("expressionsjson", value);
    }

}
