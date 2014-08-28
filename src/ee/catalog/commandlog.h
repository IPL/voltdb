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

#ifndef CATALOG_COMMANDLOG_H_
#define CATALOG_COMMANDLOG_H_

#include <string>
#include "catalogtype.h"
#include "catalogmap.h"

namespace catalog {

/**
 * Configuration for a command log
 */
class CommandLog : public CatalogType {
    friend class Catalog;
    friend class CatalogMap<CommandLog>;

protected:
    CommandLog(Catalog * catalog, CatalogType * parent, const std::string &path, const std::string &name);
    bool m_enabled;
    bool m_synchronous;
    int32_t m_fsyncInterval;
    int32_t m_maxTxns;
    int32_t m_logSize;
    std::string m_logPath;
    std::string m_internalSnapshotPath;

    virtual void update();

    virtual CatalogType * addChild(const std::string &collectionName, const std::string &name);
    virtual CatalogType * getChild(const std::string &collectionName, const std::string &childName) const;
    virtual bool removeChild(const std::string &collectionName, const std::string &childName);

public:
    ~CommandLog();

    /** GETTER: Is command logging enabled */
    bool enabled() const;
    /** GETTER: Should commands be executed only once durable */
    bool synchronous() const;
    /** GETTER: How often commands should be written to disk */
    int32_t fsyncInterval() const;
    /** GETTER: How many txns waiting to go to disk should trigger a flush */
    int32_t maxTxns() const;
    /** GETTER: Size of the command log in megabytes */
    int32_t logSize() const;
    /** GETTER: Directory to store log files */
    const std::string & logPath() const;
    /** GETTER: Directory to store internal snapshots for the command log */
    const std::string & internalSnapshotPath() const;
};

} // namespace catalog

#endif //  CATALOG_COMMANDLOG_H_
