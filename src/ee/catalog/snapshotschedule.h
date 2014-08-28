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

#ifndef CATALOG_SNAPSHOTSCHEDULE_H_
#define CATALOG_SNAPSHOTSCHEDULE_H_

#include <string>
#include "catalogtype.h"
#include "catalogmap.h"

namespace catalog {

/**
 * A schedule for the database to follow when creating automated snapshots
 */
class SnapshotSchedule : public CatalogType {
    friend class Catalog;
    friend class CatalogMap<SnapshotSchedule>;

protected:
    SnapshotSchedule(Catalog * catalog, CatalogType * parent, const std::string &path, const std::string &name);
    bool m_enabled;
    std::string m_frequencyUnit;
    int32_t m_frequencyValue;
    int32_t m_retain;
    std::string m_path;
    std::string m_prefix;

    virtual void update();

    virtual CatalogType * addChild(const std::string &collectionName, const std::string &name);
    virtual CatalogType * getChild(const std::string &collectionName, const std::string &childName) const;
    virtual bool removeChild(const std::string &collectionName, const std::string &childName);

public:
    ~SnapshotSchedule();

    /** GETTER: Is this auto snapshot schedule enabled? */
    bool enabled() const;
    /** GETTER: Unit of time frequency is specified in */
    const std::string & frequencyUnit() const;
    /** GETTER: Frequency in some unit */
    int32_t frequencyValue() const;
    /** GETTER: How many snapshots to retain */
    int32_t retain() const;
    /** GETTER: Path where snapshots should be stored */
    const std::string & path() const;
    /** GETTER: Prefix for snapshot filenames */
    const std::string & prefix() const;
};

} // namespace catalog

#endif //  CATALOG_SNAPSHOTSCHEDULE_H_
