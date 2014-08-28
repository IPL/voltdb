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
 * Container for deployment systemsettings element
 */
public class Systemsettings extends CatalogType {

    int m_maxtemptablesize;
    int m_snapshotpriority;
    int m_elasticPauseTime;
    int m_elasticThroughput;

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("maxtemptablesize", m_maxtemptablesize);
        m_fields.put("snapshotpriority", m_snapshotpriority);
        m_fields.put("elasticPauseTime", m_elasticPauseTime);
        m_fields.put("elasticThroughput", m_elasticThroughput);
    }

    void update() {
        m_maxtemptablesize = (Integer) m_fields.get("maxtemptablesize");
        m_snapshotpriority = (Integer) m_fields.get("snapshotpriority");
        m_elasticPauseTime = (Integer) m_fields.get("elasticPauseTime");
        m_elasticThroughput = (Integer) m_fields.get("elasticThroughput");
    }

    /** GETTER: The maximum allocation size for temp tables in the EE */
    public int getMaxtemptablesize() {
        return m_maxtemptablesize;
    }

    /** GETTER: The priority of snapshot work */
    public int getSnapshotpriority() {
        return m_snapshotpriority;
    }

    /** GETTER: Maximum pause time for rebalancing */
    public int getElasticpausetime() {
        return m_elasticPauseTime;
    }

    /** GETTER: Target throughput in megabytes for elasticity */
    public int getElasticthroughput() {
        return m_elasticThroughput;
    }

    /** SETTER: The maximum allocation size for temp tables in the EE */
    public void setMaxtemptablesize(int value) {
        m_maxtemptablesize = value; m_fields.put("maxtemptablesize", value);
    }

    /** SETTER: The priority of snapshot work */
    public void setSnapshotpriority(int value) {
        m_snapshotpriority = value; m_fields.put("snapshotpriority", value);
    }

    /** SETTER: Maximum pause time for rebalancing */
    public void setElasticpausetime(int value) {
        m_elasticPauseTime = value; m_fields.put("elasticPauseTime", value);
    }

    /** SETTER: Target throughput in megabytes for elasticity */
    public void setElasticthroughput(int value) {
        m_elasticThroughput = value; m_fields.put("elasticThroughput", value);
    }

}
