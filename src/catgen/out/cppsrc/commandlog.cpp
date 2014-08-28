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
#include "commandlog.h"
#include "catalog.h"

using namespace catalog;
using namespace std;

CommandLog::CommandLog(Catalog *catalog, CatalogType *parent, const string &path, const string &name)
: CatalogType(catalog, parent, path, name)
{
    CatalogValue value;
    m_fields["enabled"] = value;
    m_fields["synchronous"] = value;
    m_fields["fsyncInterval"] = value;
    m_fields["maxTxns"] = value;
    m_fields["logSize"] = value;
    m_fields["logPath"] = value;
    m_fields["internalSnapshotPath"] = value;
}

CommandLog::~CommandLog() {
}

void CommandLog::update() {
    m_enabled = m_fields["enabled"].intValue;
    m_synchronous = m_fields["synchronous"].intValue;
    m_fsyncInterval = m_fields["fsyncInterval"].intValue;
    m_maxTxns = m_fields["maxTxns"].intValue;
    m_logSize = m_fields["logSize"].intValue;
    m_logPath = m_fields["logPath"].strValue.c_str();
    m_internalSnapshotPath = m_fields["internalSnapshotPath"].strValue.c_str();
}

CatalogType * CommandLog::addChild(const std::string &collectionName, const std::string &childName) {
    return NULL;
}

CatalogType * CommandLog::getChild(const std::string &collectionName, const std::string &childName) const {
    return NULL;
}

bool CommandLog::removeChild(const std::string &collectionName, const std::string &childName) {
    assert (m_childCollections.find(collectionName) != m_childCollections.end());
    return false;
}

bool CommandLog::enabled() const {
    return m_enabled;
}

bool CommandLog::synchronous() const {
    return m_synchronous;
}

int32_t CommandLog::fsyncInterval() const {
    return m_fsyncInterval;
}

int32_t CommandLog::maxTxns() const {
    return m_maxTxns;
}

int32_t CommandLog::logSize() const {
    return m_logSize;
}

const string & CommandLog::logPath() const {
    return m_logPath;
}

const string & CommandLog::internalSnapshotPath() const {
    return m_internalSnapshotPath;
}

