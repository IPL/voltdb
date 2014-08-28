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
 * Run-time deployment settings
 */
public class Deployment extends CatalogType {

    int m_hostcount;
    int m_kfactor;
    int m_sitesperhost;
    CatalogMap<Systemsettings> m_systemsettings;

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("hostcount", m_hostcount);
        m_fields.put("kfactor", m_kfactor);
        m_fields.put("sitesperhost", m_sitesperhost);
        m_systemsettings = new CatalogMap<Systemsettings>(catalog, this, path + "/" + "systemsettings", Systemsettings.class);
        m_childCollections.put("systemsettings", m_systemsettings);
    }

    void update() {
        m_hostcount = (Integer) m_fields.get("hostcount");
        m_kfactor = (Integer) m_fields.get("kfactor");
        m_sitesperhost = (Integer) m_fields.get("sitesperhost");
    }

    /** GETTER: The number of hosts in the cluster */
    public int getHostcount() {
        return m_hostcount;
    }

    /** GETTER: The required k-safety factor */
    public int getKfactor() {
        return m_kfactor;
    }

    /** GETTER: The number of execution sites per host */
    public int getSitesperhost() {
        return m_sitesperhost;
    }

    /** GETTER: Values from the systemsettings element */
    public CatalogMap<Systemsettings> getSystemsettings() {
        return m_systemsettings;
    }

    /** SETTER: The number of hosts in the cluster */
    public void setHostcount(int value) {
        m_hostcount = value; m_fields.put("hostcount", value);
    }

    /** SETTER: The required k-safety factor */
    public void setKfactor(int value) {
        m_kfactor = value; m_fields.put("kfactor", value);
    }

    /** SETTER: The number of execution sites per host */
    public void setSitesperhost(int value) {
        m_sitesperhost = value; m_fields.put("sitesperhost", value);
    }

}
