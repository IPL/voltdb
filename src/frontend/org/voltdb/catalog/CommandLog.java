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
 * Configuration for a command log
 */
public class CommandLog extends CatalogType {

    boolean m_enabled;
    boolean m_synchronous;
    int m_fsyncInterval;
    int m_maxTxns;
    int m_logSize;
    String m_logPath = new String();
    String m_internalSnapshotPath = new String();

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_fields.put("enabled", m_enabled);
        m_fields.put("synchronous", m_synchronous);
        m_fields.put("fsyncInterval", m_fsyncInterval);
        m_fields.put("maxTxns", m_maxTxns);
        m_fields.put("logSize", m_logSize);
        m_fields.put("logPath", m_logPath);
        m_fields.put("internalSnapshotPath", m_internalSnapshotPath);
    }

    void update() {
        m_enabled = (Boolean) m_fields.get("enabled");
        m_synchronous = (Boolean) m_fields.get("synchronous");
        m_fsyncInterval = (Integer) m_fields.get("fsyncInterval");
        m_maxTxns = (Integer) m_fields.get("maxTxns");
        m_logSize = (Integer) m_fields.get("logSize");
        m_logPath = (String) m_fields.get("logPath");
        m_internalSnapshotPath = (String) m_fields.get("internalSnapshotPath");
    }

    /** GETTER: Is command logging enabled */
    public boolean getEnabled() {
        return m_enabled;
    }

    /** GETTER: Should commands be executed only once durable */
    public boolean getSynchronous() {
        return m_synchronous;
    }

    /** GETTER: How often commands should be written to disk */
    public int getFsyncinterval() {
        return m_fsyncInterval;
    }

    /** GETTER: How many txns waiting to go to disk should trigger a flush */
    public int getMaxtxns() {
        return m_maxTxns;
    }

    /** GETTER: Size of the command log in megabytes */
    public int getLogsize() {
        return m_logSize;
    }

    /** GETTER: Directory to store log files */
    public String getLogpath() {
        return m_logPath;
    }

    /** GETTER: Directory to store internal snapshots for the command log */
    public String getInternalsnapshotpath() {
        return m_internalSnapshotPath;
    }

    /** SETTER: Is command logging enabled */
    public void setEnabled(boolean value) {
        m_enabled = value; m_fields.put("enabled", value);
    }

    /** SETTER: Should commands be executed only once durable */
    public void setSynchronous(boolean value) {
        m_synchronous = value; m_fields.put("synchronous", value);
    }

    /** SETTER: How often commands should be written to disk */
    public void setFsyncinterval(int value) {
        m_fsyncInterval = value; m_fields.put("fsyncInterval", value);
    }

    /** SETTER: How many txns waiting to go to disk should trigger a flush */
    public void setMaxtxns(int value) {
        m_maxTxns = value; m_fields.put("maxTxns", value);
    }

    /** SETTER: Size of the command log in megabytes */
    public void setLogsize(int value) {
        m_logSize = value; m_fields.put("logSize", value);
    }

    /** SETTER: Directory to store log files */
    public void setLogpath(String value) {
        m_logPath = value; m_fields.put("logPath", value);
    }

    /** SETTER: Directory to store internal snapshots for the command log */
    public void setInternalsnapshotpath(String value) {
        m_internalSnapshotPath = value; m_fields.put("internalSnapshotPath", value);
    }

}
