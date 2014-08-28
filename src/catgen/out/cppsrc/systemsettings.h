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

#ifndef CATALOG_SYSTEMSETTINGS_H_
#define CATALOG_SYSTEMSETTINGS_H_

#include <string>
#include "catalogtype.h"
#include "catalogmap.h"

namespace catalog {

/**
 * Container for deployment systemsettings element
 */
class Systemsettings : public CatalogType {
    friend class Catalog;
    friend class CatalogMap<Systemsettings>;

protected:
    Systemsettings(Catalog * catalog, CatalogType * parent, const std::string &path, const std::string &name);
    int32_t m_maxtemptablesize;
    int32_t m_snapshotpriority;
    int32_t m_elasticPauseTime;
    int32_t m_elasticThroughput;

    virtual void update();

    virtual CatalogType * addChild(const std::string &collectionName, const std::string &name);
    virtual CatalogType * getChild(const std::string &collectionName, const std::string &childName) const;
    virtual bool removeChild(const std::string &collectionName, const std::string &childName);

public:
    ~Systemsettings();

    /** GETTER: The maximum allocation size for temp tables in the EE */
    int32_t maxtemptablesize() const;
    /** GETTER: The priority of snapshot work */
    int32_t snapshotpriority() const;
    /** GETTER: Maximum pause time for rebalancing */
    int32_t elasticPauseTime() const;
    /** GETTER: Target throughput in megabytes for elasticity */
    int32_t elasticThroughput() const;
};

} // namespace catalog

#endif //  CATALOG_SYSTEMSETTINGS_H_
