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
 * Export connector
 */
public class Connector extends CatalogType {

    String m_loaderclass = new String();
    boolean m_enabled;
    CatalogMap<ConnectorTableInfo> m_tableInfo;
    CatalogMap<ConnectorProperty> m_config;

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("loaderclass", m_loaderclass);
        m_fields.put("enabled", m_enabled);
        m_tableInfo = new CatalogMap<ConnectorTableInfo>(catalog, this, path + "/" + "tableInfo", ConnectorTableInfo.class);
        m_childCollections.put("tableInfo", m_tableInfo);
        m_config = new CatalogMap<ConnectorProperty>(catalog, this, path + "/" + "config", ConnectorProperty.class);
        m_childCollections.put("config", m_config);
    }

    void update() {
        m_loaderclass = (String) m_fields.get("loaderclass");
        m_enabled = (Boolean) m_fields.get("enabled");
    }

    /** GETTER: The class name of the connector */
    public String getLoaderclass() {
        return m_loaderclass;
    }

    /** GETTER: Is the connector enabled */
    public boolean getEnabled() {
        return m_enabled;
    }

    /** GETTER: Per table configuration */
    public CatalogMap<ConnectorTableInfo> getTableinfo() {
        return m_tableInfo;
    }

    /** GETTER: Connector configuration properties */
    public CatalogMap<ConnectorProperty> getConfig() {
        return m_config;
    }

    /** SETTER: The class name of the connector */
    public void setLoaderclass(String value) {
        m_loaderclass = value; m_fields.put("loaderclass", value);
    }

    /** SETTER: Is the connector enabled */
    public void setEnabled(boolean value) {
        m_enabled = value; m_fields.put("enabled", value);
    }

}
