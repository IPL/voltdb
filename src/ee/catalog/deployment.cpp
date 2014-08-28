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
#include "deployment.h"
#include "catalog.h"
#include "systemsettings.h"

using namespace catalog;
using namespace std;

Deployment::Deployment(Catalog *catalog, CatalogType *parent, const string &path, const string &name)
: CatalogType(catalog, parent, path, name),
  m_systemsettings(catalog, this, path + "/" + "systemsettings")
{
    CatalogValue value;
    m_fields["hostcount"] = value;
    m_fields["kfactor"] = value;
    m_fields["sitesperhost"] = value;
    m_childCollections["systemsettings"] = &m_systemsettings;
}

Deployment::~Deployment() {
    std::map<std::string, Systemsettings*>::const_iterator systemsettings_iter = m_systemsettings.begin();
    while (systemsettings_iter != m_systemsettings.end()) {
        delete systemsettings_iter->second;
        systemsettings_iter++;
    }
    m_systemsettings.clear();

}

void Deployment::update() {
    m_hostcount = m_fields["hostcount"].intValue;
    m_kfactor = m_fields["kfactor"].intValue;
    m_sitesperhost = m_fields["sitesperhost"].intValue;
}

CatalogType * Deployment::addChild(const std::string &collectionName, const std::string &childName) {
    if (collectionName.compare("systemsettings") == 0) {
        CatalogType *exists = m_systemsettings.get(childName);
        if (exists)
            return NULL;
        return m_systemsettings.add(childName);
    }
    return NULL;
}

CatalogType * Deployment::getChild(const std::string &collectionName, const std::string &childName) const {
    if (collectionName.compare("systemsettings") == 0)
        return m_systemsettings.get(childName);
    return NULL;
}

bool Deployment::removeChild(const std::string &collectionName, const std::string &childName) {
    assert (m_childCollections.find(collectionName) != m_childCollections.end());
    if (collectionName.compare("systemsettings") == 0) {
        return m_systemsettings.remove(childName);
    }
    return false;
}

int32_t Deployment::hostcount() const {
    return m_hostcount;
}

int32_t Deployment::kfactor() const {
    return m_kfactor;
}

int32_t Deployment::sitesperhost() const {
    return m_sitesperhost;
}

const CatalogMap<Systemsettings> & Deployment::systemsettings() const {
    return m_systemsettings;
}

