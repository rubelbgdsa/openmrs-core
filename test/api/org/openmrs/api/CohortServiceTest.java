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
package org.openmrs.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Cohort;
import org.openmrs.api.context.Context;
import org.openmrs.cohort.CohortDefinition;
import org.openmrs.reporting.PatientCharacteristicFilter;
import org.openmrs.reporting.PatientSearch;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;
import org.openmrs.test.Verifies;

/**
 * Tests methods in the CohortService class TODO add all the rest of the tests
 */
public class CohortServiceTest extends BaseContextSensitiveTest {
	
	protected static final String CREATE_PATIENT_XML = "org/openmrs/api/include/PatientServiceTest-createPatient.xml";
	
	protected static CohortService service = null;
	
	/**
	 * Run this before each unit test in this class. The "@Before" method in
	 * {@link BaseContextSensitiveTest} is run right before this method.
	 * 
	 * @throws Exception
	 */
	@Before
	public void runBeforeAllTests() throws Exception {
		service = Context.getCohortService();
	}
	
	@Test
	@SkipBaseSetup
	public void shouldPatientSearchCohortDefinitionProvider() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet(CREATE_PATIENT_XML);
		authenticate();
		
		CohortDefinition def = PatientSearch.createFilterSearch(PatientCharacteristicFilter.class);
		Cohort result = service.evaluate(def, null);
		assertNotNull("Should not return null", result);
		assertEquals("Should return one member", 1, result.size());
	}
	
	@Test
	public void shouldOnlyGetNonVoidedCohortsByName() throws Exception {
		executeDataSet("org/openmrs/api/include/CohortServiceTest-cohort.xml");
		
		// make sure we have two cohorts with the same name and the first is voided
		List<Cohort> allCohorts = service.getAllCohorts(true);
		assertNotNull(allCohorts);
		assertEquals(2, allCohorts.size());
		assertTrue(allCohorts.get(0).isVoided());
		assertFalse(allCohorts.get(1).isVoided());
		
		// now do the actual test: getCohort by name and expect a non voided cohort
		Cohort exampleCohort = service.getCohort("Example Cohort");
		assertNotNull(exampleCohort);
		assertEquals(2, exampleCohort.size());
		assertFalse(exampleCohort.isVoided());
	}
	
	
	/**
	 * @see {@link CohortService#purgeCohort(Cohort)}
	 */
	@Test
	@Verifies(value = "should delete cohort from database", method = "purgeCohort(Cohort)")
	public void purgeCohort_shouldDeleteCohortFromDatabase() throws Exception {
		executeDataSet("org/openmrs/api/include/CohortServiceTest-cohort.xml");
		List<Cohort> allCohorts = service.getAllCohorts(true);
		assertEquals(2, allCohorts.size());
		service.purgeCohort(allCohorts.get(0));
		allCohorts = service.getAllCohorts(true);
		assertEquals(1, allCohorts.size());
	}
	
	/**
	 * @see {@link CohortService#getCohorts(String)}
	 */
	@Test
	@Verifies(value = "should match cohorts by partial name", method = "getCohorts(String)")
	public void getCohorts_shouldMatchCohortsByPartialName() throws Exception {
		executeDataSet("org/openmrs/api/include/CohortServiceTest-cohort.xml");
		List<Cohort> matchedCohorts = service.getCohorts("Example");
		assertEquals(2, matchedCohorts.size());
		matchedCohorts = service.getCohorts("e Coh");
		assertEquals(2, matchedCohorts.size());
		matchedCohorts = service.getCohorts("hort");
		assertEquals(2, matchedCohorts.size());
		matchedCohorts = service.getCohorts("Examples");
		assertEquals(0, matchedCohorts.size());
	}
}
