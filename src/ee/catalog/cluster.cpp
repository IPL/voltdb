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

#include <cassert>
#include "cluster.h"
#include "catalog.h"
#include "deployment.h"
#include "commandlog.h"
#include "database.h"
#include "snapshotschedule.h"

using namespace catalog;
using namespace std;

Cluster::Cluster(Catalog *catalog, CatalogType *parent, const string &path, const string &name)
: CatalogType(catalog, parent, path, name),
  m_databases(catalog, this, path + "/" + "databases"), m_deployment(catalog, this, path + "/" + "deployment"), m_faultSnapshots(catalog, this, path + "/" + "faultSnapshots"), m_logconfig(catalog, this, path + "/" + "logconfig")
{
    CatalogValue value;
    m_childCollections["databases"] = &m_databases;
    m_childCollections["deployment"] = &m_deployment;
    m_fields["localepoch"] = value;
    m_fields["securityEnabled"] = value;
    m_fields["httpdportno"] = value;
    m_fields["jsonapi"] = value;
    m_fields["networkpartition"] = value;
    m_fields["voltRoot"] = value;
    m_fields["exportOverflow"] = value;
    m_childCollections["faultSnapshots"] = &m_faultSnapshots;
    m_fields["adminport"] = value;
    m_fields["adminstartup"] = value;
    m_childCollections["logconfig"] = &m_logconfig;
    m_fields["heartbeatTimeout"] = value;
    m_fields["useadhocschema"] = value;
}

Cluster::~Cluster() {
    std::map<std::string, Database*>::const_iterator database_iter = m_databases.begin();
    while (database_iter != m_databases.end()) {
        delete database_iter->second;
        database_iter++;
    }
    m_databases.clear();

    std::map<std::string, Deployment*>::const_iterator deployment_iter = m_deployment.begin();
    while (deployment_iter != m_deployment.end()) {
        delete deployment_iter->second;
        deployment_iter++;
    }
    m_deployment.clear();

    std::map<std::string, SnapshotSchedule*>::const_iterator snapshotschedule_iter = m_faultSnapshots.begin();
    while (snapshotschedule_iter != m_faultSnapshots.end()) {
        delete snapshotschedule_iter->second;
        snapshotschedule_iter++;
    }
    m_faultSnapshots.clear();

    std::map<std::string, CommandLog*>::const_iterator commandlog_iter = m_logconfig.begin();
    while (commandlog_iter != m_logconfig.end()) {
        delete commandlog_iter->second;
        commandlog_iter++;
    }
    m_logconfig.clear();

}

void Cluster::update() {
    m_localepoch = m_fields["localepoch"].intValue;
    m_securityEnabled = m_fields["securityEnabled"].intValue;
    m_httpdportno = m_fields["httpdportno"].intValue;
    m_jsonapi = m_fields["jsonapi"].intValue;
    m_networkpartition = m_fields["networkpartition"].intValue;
    m_voltRoot = m_fields["voltRoot"].strValue.c_str();
    m_exportOverflow = m_fields["exportOverflow"].strValue.c_str();
    m_adminport = m_fields["adminport"].intValue;
    m_adminstartup = m_fields["adminstartup"].intValue;
    m_heartbeatTimeout = m_fields["heartbeatTimeout"].intValue;
    m_useadhocschema = m_fields["useadhocschema"].intValue;
}

CatalogType * Cluster::addChild(const std::string &collectionName, const std::string &childName) {
    if (collectionName.compare("databases") == 0) {
        CatalogType *exists = m_databases.get(childName);
        if (exists)
            return NULL;
        return m_databases.add(childName);
    }
    if (collectionName.compare("deployment") == 0) {
        CatalogType *exists = m_deployment.get(childName);
        if (exists)
            return NULL;
        return m_deployment.add(childName);
    }
    if (collectionName.compare("faultSnapshots") == 0) {
        CatalogType *exists = m_faultSnapshots.get(childName);
        if (exists)
            return NULL;
        return m_faultSnapshots.add(childName);
    }
    if (collectionName.compare("logconfig") == 0) {
        CatalogType *exists = m_logconfig.get(childName);
        if (exists)
            return NULL;
        return m_logconfig.add(childName);
    }
    return NULL;
}

CatalogType * Cluster::getChild(const std::string &collectionName, const std::string &childName) const {
    if (collectionName.compare("databases") == 0)
        return m_databases.get(childName);
    if (collectionName.compare("deployment") == 0)
        return m_deployment.get(childName);
    if (collectionName.compare("faultSnapshots") == 0)
        return m_faultSnapshots.get(childName);
    if (collectionName.compare("logconfig") == 0)
        return m_logconfig.get(childName);
    return NULL;
}

bool Cluster::removeChild(const std::string &collectionName, const std::string &childName) {
    assert (m_childCollections.find(collectionName) != m_childCollections.end());
    if (collectionName.compare("databases") == 0) {
        return m_databases.remove(childName);
    }
    if (collectionName.compare("deployment") == 0) {
        return m_deployment.remove(childName);
    }
    if (collectionName.compare("faultSnapshots") == 0) {
        return m_faultSnapshots.remove(childName);
    }
    if (collectionName.compare("logconfig") == 0) {
        return m_logconfig.remove(childName);
    }
    return false;
}

const CatalogMap<Database> & Cluster::databases() const {
    return m_databases;
}

const CatalogMap<Deployment> & Cluster::deployment() const {
    return m_deployment;
}

int32_t Cluster::localepoch() const {
    return m_localepoch;
}

bool Cluster::securityEnabled() const {
    return m_securityEnabled;
}

int32_t Cluster::httpdportno() const {
    return m_httpdportno;
}

bool Cluster::jsonapi() const {
    return m_jsonapi;
}

bool Cluster::networkpartition() const {
    return m_networkpartition;
}

const string & Cluster::voltRoot() const {
    return m_voltRoot;
}

const string & Cluster::exportOverflow() const {
    return m_exportOverflow;
}

const CatalogMap<SnapshotSchedule> & Cluster::faultSnapshots() const {
    return m_faultSnapshots;
}

int32_t Cluster::adminport() const {
    return m_adminport;
}

bool Cluster::adminstartup() const {
    return m_adminstartup;
}

const CatalogMap<CommandLog> & Cluster::logconfig() const {
    return m_logconfig;
}

int32_t Cluster::heartbeatTimeout() const {
    return m_heartbeatTimeout;
}

bool Cluster::useadhocschema() const {
    return m_useadhocschema;
}

