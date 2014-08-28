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
#include "index.h"
#include "catalog.h"
#include "columnref.h"

using namespace catalog;
using namespace std;

Index::Index(Catalog *catalog, CatalogType *parent, const string &path, const string &name)
: CatalogType(catalog, parent, path, name),
  m_columns(catalog, this, path + "/" + "columns")
{
    CatalogValue value;
    m_fields["unique"] = value;
    m_fields["assumeUnique"] = value;
    m_fields["countable"] = value;
    m_fields["type"] = value;
    m_childCollections["columns"] = &m_columns;
    m_fields["expressionsjson"] = value;
}

Index::~Index() {
    std::map<std::string, ColumnRef*>::const_iterator columnref_iter = m_columns.begin();
    while (columnref_iter != m_columns.end()) {
        delete columnref_iter->second;
        columnref_iter++;
    }
    m_columns.clear();

}

void Index::update() {
    m_unique = m_fields["unique"].intValue;
    m_assumeUnique = m_fields["assumeUnique"].intValue;
    m_countable = m_fields["countable"].intValue;
    m_type = m_fields["type"].intValue;
    m_expressionsjson = m_fields["expressionsjson"].strValue.c_str();
}

CatalogType * Index::addChild(const std::string &collectionName, const std::string &childName) {
    if (collectionName.compare("columns") == 0) {
        CatalogType *exists = m_columns.get(childName);
        if (exists)
            return NULL;
        return m_columns.add(childName);
    }
    return NULL;
}

CatalogType * Index::getChild(const std::string &collectionName, const std::string &childName) const {
    if (collectionName.compare("columns") == 0)
        return m_columns.get(childName);
    return NULL;
}

bool Index::removeChild(const std::string &collectionName, const std::string &childName) {
    assert (m_childCollections.find(collectionName) != m_childCollections.end());
    if (collectionName.compare("columns") == 0) {
        return m_columns.remove(childName);
    }
    return false;
}

bool Index::unique() const {
    return m_unique;
}

bool Index::assumeUnique() const {
    return m_assumeUnique;
}

bool Index::countable() const {
    return m_countable;
}

int32_t Index::type() const {
    return m_type;
}

const CatalogMap<ColumnRef> & Index::columns() const {
    return m_columns;
}

const string & Index::expressionsjson() const {
    return m_expressionsjson;
}

