/**
 * Copyright 2017 Telefonica Investigación y Desarrollo, S.A.U
 *
 * This file is part of fiware-cygnus (FI-WARE project).
 *
 * fiware-cygnus is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * fiware-cygnus is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with fiware-cygnus. If not, see
 * http://www.gnu.org/licenses/.
 *
 * For those usages not covered by the GNU Affero General Public License please contact with iot_support at tid dot es
 */
package com.telefonica.iot.cygnus.backends.mysql;

import com.telefonica.iot.cygnus.log.CygnusLogger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author frb
 */
public class MySQLCache {
    
    private static final CygnusLogger LOGGER = new CygnusLogger(MySQLCache.class);
    private final HashMap<String, ArrayList<String>> hierarchy;
    
    /**
     * Constructor.
     */
    public MySQLCache() {
        hierarchy = new HashMap<>();
    } // MySQLCache
    
    /**
     * Adds a db name to the cache.
     * @param dbName
     * @return True if the db is added, false otherwise. 
     */
    public boolean addDb(String dbName) {
        if (hierarchy.containsKey(dbName)) {
            LOGGER.debug("'" + dbName + "' not added to the cache, since already existing");
            return false;
        } else {
            hierarchy.put(dbName, new ArrayList<String>());
            LOGGER.debug("'" + dbName + "' added to the cache");
            return true;
        } // if else
    } // addDb
    
    /**
     * Adds a table name within in a db name to the cache.
     * @param dbName
     * @param tableName
     * @return True if the table is added, false otherwise
     */
    public boolean addTable(String dbName, String tableName) {
        ArrayList<String> tables = hierarchy.get(dbName);
        
        if (tables != null) {
            if (tables.contains(tableName)) {
                LOGGER.debug("'" + tableName + "' not added to the cache, since already existing");
                return false;
            } else {
                tables.add(tableName);
                LOGGER.debug("'" + tableName + "' added to the cache");
                return true;
            } // if else
        } else {
            LOGGER.debug("'" + dbName + "' was not added to the cache, since database did not exist");
            return false;
        } // if else
    } // addTable
    
    /**
     * Gets if a db name is cached.
     * @param dbName
     * @return True if the db name is cached, false otherwise.
     */
    public boolean isCachedDb(String dbName) {
        return hierarchy.containsKey(dbName);
    } // isCachedDb
    
    /**
     * Gets if a table name within a db name is cached.
     * @param dbName
     * @param tableName
     * @return True if the table name is cached, false otherwise.
     */
    public boolean isCachedTable(String dbName, String tableName) {
        ArrayList<String> tables = hierarchy.get(dbName);
        
        if (tables == null) {
            return false;
        } else {
            return tables.contains(tableName);
        } // if else
    } // isCachedTable
    
} // MySQLCache
