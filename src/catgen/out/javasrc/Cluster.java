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
 * A set of connected hosts running one or more database application contexts
 */
public class Cluster extends CatalogType {

    CatalogMap<Database> m_databases;
    CatalogMap<Deployment> m_deployment;
    int m_localepoch;
    boolean m_securityEnabled;
    int m_httpdportno;
    boolean m_jsonapi;
    boolean m_networkpartition;
    String m_voltRoot = new String();
    String m_exportOverflow = new String();
    CatalogMap<SnapshotSchedule> m_faultSnapshots;
    int m_adminport;
    boolean m_adminstartup;
    CatalogMap<CommandLog> m_logconfig;
    int m_heartbeatTimeout;
    boolean m_useadhocschema;

    void setBaseValues(Catalog catalog, CatalogType parent, String path, String name) {
        super.setBaseValues(catalog, parent, path, name);
        m_databases = new CatalogMap<Database>(catalog, this, path + "/" + "databases", Database.class);
        m_childCollections.put("databases", m_databases);
        m_deployment = new CatalogMap<Deployment>(catalog, this, path + "/" + "deployment", Deployment.class);
        m_childCollections.put("deployment", m_deployment);
        m_fields.put("localepoch", m_localepoch);
        m_fields.put("securityEnabled", m_securityEnabled);
        m_fields.put("httpdportno", m_httpdportno);
        m_fields.put("jsonapi", m_jsonapi);
        m_fields.put("networkpartition", m_networkpartition);
        m_fields.put("voltRoot", m_voltRoot);
        m_fields.put("exportOverflow", m_exportOverflow);
        m_faultSnapshots = new CatalogMap<SnapshotSchedule>(catalog, this, path + "/" + "faultSnapshots", SnapshotSchedule.class);
        m_childCollections.put("faultSnapshots", m_faultSnapshots);
        m_fields.put("adminport", m_adminport);
        m_fields.put("adminstartup", m_adminstartup);
        m_logconfig = new CatalogMap<CommandLog>(catalog, this, path + "/" + "logconfig", CommandLog.class);
        m_childCollections.put("logconfig", m_logconfig);
        m_fields.put("heartbeatTimeout", m_heartbeatTimeout);
        m_fields.put("useadhocschema", m_useadhocschema);
    }

    void update() {
        m_localepoch = (Integer) m_fields.get("localepoch");
        m_securityEnabled = (Boolean) m_fields.get("securityEnabled");
        m_httpdportno = (Integer) m_fields.get("httpdportno");
        m_jsonapi = (Boolean) m_fields.get("jsonapi");
        m_networkpartition = (Boolean) m_fields.get("networkpartition");
        m_voltRoot = (String) m_fields.get("voltRoot");
        m_exportOverflow = (String) m_fields.get("exportOverflow");
        m_adminport = (Integer) m_fields.get("adminport");
        m_adminstartup = (Boolean) m_fields.get("adminstartup");
        m_heartbeatTimeout = (Integer) m_fields.get("heartbeatTimeout");
        m_useadhocschema = (Boolean) m_fields.get("useadhocschema");
    }

    /** GETTER: The set of databases the cluster is running */
    public CatalogMap<Database> getDatabases() {
        return m_databases;
    }

    /** GETTER: Storage for settings passed in on deployment */
    public CatalogMap<Deployment> getDeployment() {
        return m_deployment;
    }

    /** GETTER: The number of seconds since the epoch that we're calling our local epoch */
    public int getLocalepoch() {
        return m_localepoch;
    }

    /** GETTER: Whether security and authentication should be enabled/disabled */
    public boolean getSecurityenabled() {
        return m_securityEnabled;
    }

    /** GETTER: The port number httpd will listen on. A 0 value implies 8080. */
    public int getHttpdportno() {
        return m_httpdportno;
    }

    /** GETTER: Is the http/json interface enabled? */
    public boolean getJsonapi() {
        return m_jsonapi;
    }

    /** GETTER: Is network partition detection enabled? */
    public boolean getNetworkpartition() {
        return m_networkpartition;
    }

    /** GETTER: Directory tree where snapshots, ppd snapshots, export data etc. will be output to */
    public String getVoltroot() {
        return m_voltRoot;
    }

    /** GETTER: Directory where export data should overflow to */
    public String getExportoverflow() {
        return m_exportOverflow;
    }

    /** GETTER: Configuration for snapshots generated in response to faults. */
    public CatalogMap<SnapshotSchedule> getFaultsnapshots() {
        return m_faultSnapshots;
    }

    /** GETTER: The port number of the admin port */
    public int getAdminport() {
        return m_adminport;
    }

    /** GETTER: Does the server start in admin mode? */
    public boolean getAdminstartup() {
        return m_adminstartup;
    }

    /** GETTER: Command log configuration */
    public CatalogMap<CommandLog> getLogconfig() {
        return m_logconfig;
    }

    /** GETTER: How long to wait, in seconds, between messages before deciding a host is dead */
    public int getHeartbeattimeout() {
        return m_heartbeatTimeout;
    }

    /** GETTER: Manage the database schemas via catalog updates or adhoc DDL */
    public boolean getUseadhocschema() {
        return m_useadhocschema;
    }

    /** SETTER: The number of seconds since the epoch that we're calling our local epoch */
    public void setLocalepoch(int value) {
        m_localepoch = value; m_fields.put("localepoch", value);
    }

    /** SETTER: Whether security and authentication should be enabled/disabled */
    public void setSecurityenabled(boolean value) {
        m_securityEnabled = value; m_fields.put("securityEnabled", value);
    }

    /** SETTER: The port number httpd will listen on. A 0 value implies 8080. */
    public void setHttpdportno(int value) {
        m_httpdportno = value; m_fields.put("httpdportno", value);
    }

    /** SETTER: Is the http/json interface enabled? */
    public void setJsonapi(boolean value) {
        m_jsonapi = value; m_fields.put("jsonapi", value);
    }

    /** SETTER: Is network partition detection enabled? */
    public void setNetworkpartition(boolean value) {
        m_networkpartition = value; m_fields.put("networkpartition", value);
    }

    /** SETTER: Directory tree where snapshots, ppd snapshots, export data etc. will be output to */
    public void setVoltroot(String value) {
        m_voltRoot = value; m_fields.put("voltRoot", value);
    }

    /** SETTER: Directory where export data should overflow to */
    public void setExportoverflow(String value) {
        m_exportOverflow = value; m_fields.put("exportOverflow", value);
    }

    /** SETTER: The port number of the admin port */
    public void setAdminport(int value) {
        m_adminport = value; m_fields.put("adminport", value);
    }

    /** SETTER: Does the server start in admin mode? */
    public void setAdminstartup(boolean value) {
        m_adminstartup = value; m_fields.put("adminstartup", value);
    }

    /** SETTER: How long to wait, in seconds, between messages before deciding a host is dead */
    public void setHeartbeattimeout(int value) {
        m_heartbeatTimeout = value; m_fields.put("heartbeatTimeout", value);
    }

    /** SETTER: Manage the database schemas via catalog updates or adhoc DDL */
    public void setUseadhocschema(boolean value) {
        m_useadhocschema = value; m_fields.put("useadhocschema", value);
    }

}
