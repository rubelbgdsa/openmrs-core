/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.api.db;

import java.util.Properties;

import org.openmrs.User;
import org.openmrs.api.context.ContextAuthenticationException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Defines the functions that the Context needs to access the database
 * 
 * @version 1.1
 */
public interface ContextDAO {
	
	/**
	 * Authenticate user with the given username and password.
	 * 
	 * @param username user's username or systemId
	 * @param password user's password
	 * @return a valid user if authentication succeeds
	 * @throws ContextAuthenticationException
	 * @should authenticate given username and password
	 * @should authenticate given systemId and password
	 * @should authenticate given systemId without hyphen and password
	 * @should not authenticate given username and incorrect password
	 * @should not authenticate given systemId and incorrect password
	 * @should not authenticate given incorrect username
	 * @should not authenticate given incorrect systemId
	 * @should not authenticate given null login
	 * @should not authenticate given empty login
	 * @should not authenticate given null password when password in database is null
	 * @should not authenticate given non null password when password in database is null
	 * @should not authenticate when password in database is empty
	 * @should give identical error messages between username and password mismatch
	 * @should lockout after eight attempts
	 * @should pass regression test for 1580
	 */
	@Transactional(noRollbackFor = ContextAuthenticationException.class)
	public User authenticate(String username, String password) throws ContextAuthenticationException;
	
	/**
	 * Open session.
	 */
	public void openSession();
	
	/**
	 * Close session.
	 */
	public void closeSession();
	
	/**
	 * Clear session.
	 */
	@Transactional
	public void clearSession();
	
	/**
	 * Used to clear a cached object out of a session in the middle of a unit of work. Future
	 * updates to this object will not be saved. Future gets of this object will not fetch this
	 * cached copy
	 * 
	 * @param obj The object to evict/remove from the session
	 * @see Context.evictFromSession(Object)
	 */
	public void evictFromSession(Object obj);
	
	/**
	 * Starts the OpenMRS System Should be called prior to any kind of activity
	 * 
	 * @param Properties
	 */
	@Transactional
	public void startup(Properties props);
	
	/**
	 * Stops the OpenMRS System Should be called after all activity has ended and application is
	 * closing
	 */
	@Transactional
	public void shutdown();
	
	/**
	 * Compares core data against the current database and inserts data into the database where
	 * necessary
	 */
	@Transactional
	public void checkCoreDataset();
	
	public void closeDatabaseConnection();
	
}
