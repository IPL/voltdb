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
 * Connector configuration property
 */
public class ConnectorProperty extends CatalogType {

    String m_name = new String();
    String m_value = new String();

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("name", m_name);
        m_fields.put("value", m_value);
    }

    void update() {
        m_name = (String) m_fields.get("name");
        m_value = (String) m_fields.get("value");
    }

    /** GETTER: Configuration property name */
    public String getName() {
        return m_name;
    }

    /** GETTER: Configuration property value */
    public String getValue() {
        return m_value;
    }

    /** SETTER: Configuration property name */
    public void setName(String value) {
        m_name = value; m_fields.put("name", value);
    }

    /** SETTER: Configuration property value */
    public void setValue(String value) {
        m_value = value; m_fields.put("value", value);
    }

}
