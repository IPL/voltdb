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

#ifndef CATALOG_CONNECTORPROPERTY_H_
#define CATALOG_CONNECTORPROPERTY_H_

#include <string>
#include "catalogtype.h"
#include "catalogmap.h"

namespace catalog {

/**
 * Connector configuration property
 */
class ConnectorProperty : public CatalogType {
    friend class Catalog;
    friend class CatalogMap<ConnectorProperty>;

protected:
    ConnectorProperty(Catalog * catalog, CatalogType * parent, const std::string &path, const std::string &name);
    std::string m_name;
    std::string m_value;

    virtual void update();

    virtual CatalogType * addChild(const std::string &collectionName, const std::string &name);
    virtual CatalogType * getChild(const std::string &collectionName, const std::string &childName) const;
    virtual bool removeChild(const std::string &collectionName, const std::string &childName);

public:
    ~ConnectorProperty();

    /** GETTER: Configuration property name */
    const std::string & name() const;
    /** GETTER: Configuration property value */
    const std::string & value() const;
};

} // namespace catalog

#endif //  CATALOG_CONNECTORPROPERTY_H_
