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
#include "systemsettings.h"
#include "catalog.h"

using namespace catalog;
using namespace std;

Systemsettings::Systemsettings(Catalog *catalog, CatalogType *parent, const string &path, const string &name)
: CatalogType(catalog, parent, path, name)
{
    CatalogValue value;
    m_fields["maxtemptablesize"] = value;
    m_fields["snapshotpriority"] = value;
    m_fields["elasticPauseTime"] = value;
    m_fields["elasticThroughput"] = value;
}

Systemsettings::~Systemsettings() {
}

void Systemsettings::update() {
    m_maxtemptablesize = m_fields["maxtemptablesize"].intValue;
    m_snapshotpriority = m_fields["snapshotpriority"].intValue;
    m_elasticPauseTime = m_fields["elasticPauseTime"].intValue;
    m_elasticThroughput = m_fields["elasticThroughput"].intValue;
}

CatalogType * Systemsettings::addChild(const std::string &collectionName, const std::string &childName) {
    return NULL;
}

CatalogType * Systemsettings::getChild(const std::string &collectionName, const std::string &childName) const {
    return NULL;
}

bool Systemsettings::removeChild(const std::string &collectionName, const std::string &childName) {
    assert (m_childCollections.find(collectionName) != m_childCollections.end());
    return false;
}

int32_t Systemsettings::maxtemptablesize() const {
    return m_maxtemptablesize;
}

int32_t Systemsettings::snapshotpriority() const {
    return m_snapshotpriority;
}

int32_t Systemsettings::elasticPauseTime() const {
    return m_elasticPauseTime;
}

int32_t Systemsettings::elasticThroughput() const {
    return m_elasticThroughput;
}

