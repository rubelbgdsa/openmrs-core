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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.Person;
import org.openmrs.PersonAttributeType;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.Relationship;
import org.openmrs.RelationshipType;
import org.openmrs.api.db.DAOException;
import org.openmrs.api.db.PatientSetDAO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PatientSetService {

	public void setPatientSetDAO(PatientSetDAO dao);

	/**
	 * @param ps The set you want to export as XML
	 * @return an XML representation of this patient-set, including patient characteristics, and
	 *         observations
	 */
	@Transactional(readOnly=true)
	public String exportXml(Cohort ps);

	@Transactional(readOnly=true)
	public String exportXml(Integer patientId);

	@Transactional(readOnly=true)
	public Cohort getAllPatients() throws DAOException;

	@Transactional(readOnly=true)
	public Cohort getPatientsByCharacteristics(String gender, Date minBirthdate, Date maxBirthdate) throws DAOException;

	@Transactional(readOnly=true)
	public Cohort getPatientsByCharacteristics(String gender, Date minBirthdate, Date maxBirthdate, Integer minAge,
	                                           Integer maxAge, Boolean aliveOnly, Boolean deadOnly) throws DAOException;
	
	@Transactional(readOnly=true)
	public Cohort getPatientsByCharacteristics(String gender, Date minBirthdate, Date maxBirthdate, Integer minAge,
			Integer maxAge, Boolean aliveOnly, Boolean deadOnly, Date effectiveDate)
			throws DAOException;

	@Transactional(readOnly=true)
	public Cohort getPatientsHavingNumericObs(Integer conceptId, TimeModifier timeModifier,
	                                          PatientSetService.Modifier modifier, Number value, Date fromDate, Date toDate);
	
	/**
	 * Searches for patients who have observations as described by the arguments to this method
	 * 
	 * @param conceptId
	 * @param timeModifier
	 * @param modifier
	 * @param value
	 * @param fromDate
	 * @param toDate
	 * @return all patients with observations matching the arguments to this method
	 * @should get patients by concept and true boolean value
	 * @should get patients by concept and false boolean value
	 */
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingObs(Integer conceptId, TimeModifier timeModifier, Modifier modifier, Object value,
			Date fromDate, Date toDate);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingEncounters(EncounterType encounterType, Location location, Form form, Date fromDate,
	                                          Date toDate, Integer minCount, Integer maxCount);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingEncounters(List<EncounterType> encounterTypeList, Location location, Form form,
	                                          Date fromDate, Date toDate, Integer minCount, Integer maxCount);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsByProgramAndState(Program program, List<ProgramWorkflowState> stateList, Date fromDate,
	                                           Date toDate);

	@Transactional(readOnly=true)
	public Cohort getPatientsInProgram(Program program, Date fromDate, Date toDate);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingDateObs(Integer conceptId, Date startTime, Date endTime);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingTextObs(Concept concept, String value, TimeModifier timeModifier);

	@Transactional(readOnly=true)
	public Cohort getPatientsHavingTextObs(Integer conceptId, String value, TimeModifier timeModifier);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingLocation(Location loc);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingLocation(Location loc, PatientLocationMethod method);

	@Transactional(readOnly=true)
	public Cohort getPatientsHavingLocation(Integer locationId);
	
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingLocation(Integer locationId, PatientLocationMethod method);

	/**
	 * Returns a Cohort of patient who had drug orders for a set of drugs active on a certain date.
	 * Can also be used to find patient with no drug orders on that date.
	 * 
	 * @param patientIds Collection of patientIds you're interested in. NULL means all patients.
	 * @param takingAny Collection of drugIds the patient is taking. (Or the empty set to mean
	 *            "any drug" or NULL to mean "no drugs")
	 * @param onDate Which date to look at the patients' drug orders. (NULL defaults to now().)
	 */
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingDrugOrder(Collection<Integer> patientIds, Collection<Integer> takingIds, Date onDate);

	/**
	 * Returns a Cohort of patient who had drug orders for a set of drugs active between a pair of
	 * dates. Can also be used to find patient with no drug orders on that date.
	 * 
	 * @param patientIds Collection of patientIds you're interested in. NULL means all patients.
	 * @param drugIds Collection of drugIds the patient is taking. (Or the empty set to mean
	 *            "any drug" or NULL to mean "no drugs")
	 * @param groupMethod whether to do NONE, ALL, or ANY of the list of specified ids.
	 * @param fromDate Beginning of date range to look at (NULL defaults to toDate if that isn't
	 *            null, or now() if it is.)
	 * @param toDate End of date range to look at (NULL defaults to fromDate if that isn't null, or
	 *            now() if it is.)
	 */
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingDrugOrder(Collection<Integer> patientIds, Collection<Integer> drugIds,
	                                         GroupMethod groupMethod, Date fromDate, Date toDate);
	
	/**
	 * @return A Cohort of patients who had drug order for particular drugs or generics, with start
	 *         dates within a range, with end dates within a range, and a reason for
	 *         discontinuation.
	 */
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingDrugOrder(List<Drug> drug, List<Concept> drugConcept, Date startDateFrom,
	                                         Date startDateTo, Date stopDateFrom, Date stopDateTo, Boolean discontinued,
	                                         List<Concept> discontinuedReason);
	
	/**
	 * At least one of attribute and value must be non-null
	 * 
	 * @param attribute if not null, look for this attribute
	 * @param value if not null, look for this value
	 * @return Cohort of patients who have a person attribute (optionally) with attributeType of
	 *         attribute and (optionally) value of value.
	 */
	@Transactional(readOnly=true)
	public Cohort getPatientsHavingPersonAttribute(PersonAttributeType attribute, String value);
	
	@Transactional(readOnly=true)
	public Map<Integer, String> getShortPatientDescriptions(Collection<Integer> patientIds);

	@Transactional(readOnly=true)
	public Map<Integer, List<Obs>> getObservations(Cohort patients, Concept concept);

	/**
	 * Date range is inclusive of both endpoints 
	 */
	@Transactional(readOnly=true)
	public Map<Integer, List<Obs>> getObservations(Cohort patients, Concept concept, Date fromDate, Date toDate);

	/**
	 * Map<patientId, List<Obs values>>
	 * 
	 * @param patients
	 * @param c
	 * @return Map<patientId, List<Obs values>>
	 */
	@Transactional(readOnly=true)
	public Map<Integer, List<List<Object>>> getObservationsValues(Cohort patients, Concept c);
	
	/**
	 * Returns a mapping from patient id to obs for concept <code>c</code> The returned List<
	 * attribute value > is [obs value, attr value, attr value, attr value...] The returned
	 * List<List< attribute value >> represents the obs rows
	 * 
	 * @param patients
	 * @param c
	 * @param attributes list of attributes
	 * @return <code>Map<patientId, List<List< attribute value >>></code>
	 */
	@Transactional(readOnly=true)
	public Map<Integer, List<List<Object>>> getObservationsValues(Cohort patients, Concept c, List<String> attributes);
	
	/**
	 * @param patients
	 * @param encType
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Encounter> getEncountersByType(Cohort patients, EncounterType encType);
	
	/**
	 * @param patients
	 * @param encTypes
	 * @param attr
	 * @return
	 */
	public Map<Integer, Object> getEncounterAttrsByType(Cohort patients, List<EncounterType> encTypes, String attr);
	
	/**
	 * @param patients
	 * @param encType
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Encounter> getEncountersByType(Cohort patients, List<EncounterType> encType);

	

	/**
	 * @param patients
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Encounter> getEncounters(Cohort patients);
	
	/**
	 * @param patients
	 * @param encType
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Encounter> getFirstEncountersByType(Cohort patients, EncounterType encType);
	
	/**
	 * @param patients
	 * @param types
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Encounter> getFirstEncountersByType(Cohort patients, List<EncounterType> types);
	

	/**
	 * @param patients
	 * @param encTypes
	 * @param attr
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Object> getFirstEncounterAttrsByType(Cohort patients, List<EncounterType> encTypes, String attr);
	
	
	/**
	 * @param patients
	 * @param className
	 * @param property
	 * @param returnAll
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Object> getPatientAttributes(Cohort patients, String className, String property, boolean returnAll);

	/**
	 * @param patients
	 * @param classNameDotProperty
	 * @param returnAll
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Object> getPatientAttributes(Cohort patients, String classNameDotProperty, boolean returnAll);

	/**
	 * @param patients
	 * @param attributeName
	 * @param joinClass
	 * @param joinProperty
	 * @param outputColumn
	 * @param returnAll
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<Integer, Object> getPersonAttributes(Cohort patients, String attributeName, String joinClass,
	                                                String joinProperty, String outputColumn, boolean returnAll);
	
	/**
	 * @param patients
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, Map<String, Object>> getCharacteristics(Cohort patients);

	/**
	 * Gets a map of patient identifiers by identifier type, indexed by patient primary key.
	 * 
	 * @param patients
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public Map<Integer, PatientIdentifier> getPatientIdentifiersByType(Cohort patients, PatientIdentifierType type);


	/**
	 * @param identifiers
	 * @return
	 */
	@Transactional(readOnly=true)
	public Cohort convertPatientIdentifier(List<String> identifiers);
	
	/**
	 * @param patientIds
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Patient> getPatients(Collection<Integer> patientIds);

	/**
	 * @param ps
	 * @deprecated The "My Patient Set" feature will be removed in 1.5
	 */
	public void setMyPatientSet(Cohort ps);

	/**
	 * @return
	 * @deprecated The "My Patient Set" feature will be removed in 1.5
	 */
	@Transactional(readOnly=true)
	public Cohort getMyPatientSet();

	/**
	 * @param ptId
	 * @deprecated The "My Patient Set" feature will be removed in 1.5
	 */
	public void addToMyPatientSet(Integer ptId);

	/**
	 * @param ptId
	 * @deprecated The "My Patient Set" feature will be removed in 1.5
	 */
	public void removeFromMyPatientSet(Integer ptId);

	/**
	 * @deprecated The "My Patient Set" feature will be removed in 1.5
	 */
	public void clearMyPatientSet();

	@Transactional(readOnly=true)
	public Map<Integer, PatientState> getCurrentStates(Cohort ps, ProgramWorkflow wf);

	@Transactional(readOnly=true)
	public Map<Integer, PatientProgram> getCurrentPatientPrograms(Cohort ps, Program program);

	@Transactional(readOnly=true)
	public Map<Integer, PatientProgram> getPatientPrograms(Cohort ps, Program program);
	
	@Transactional(readOnly=true)
	public Map<Integer, List<Relationship>> getRelationships(Cohort ps, RelationshipType relType);

	@Transactional(readOnly=true)
	public Map<Integer, List<Person>> getRelatives(Cohort ps, RelationshipType relType, boolean forwards);
	
	/**
	 * @return all active drug orders whose drug concept is in the given set (or all drugs if that's
	 *         null)
	 */
	@Transactional(readOnly=true)
	public Map<Integer, List<DrugOrder>> getCurrentDrugOrders(Cohort ps, Concept drugSet);

	/**
	 * @return all active or finished drug orders whose drug concept is in the given set (or all
	 *         drugs if that's null)
	 */
	@Transactional(readOnly=true)
	public Map<Integer, List<DrugOrder>> getDrugOrders(Cohort ps, Concept drugSet);

	/**
	 * Gets a list of encounters associated with the given form, filtered by the given patient set.
	 * 
	 * @param	patients	the patients to filter by (null will return all encounters for all patients)
	 * @param 	forms		the forms to filter by
	 */
	@Transactional(readOnly=true)
	public List<Encounter> getEncountersByForm(Cohort patients, List<Form> form);
	
	
	
	public enum Modifier {
		LESS_THAN("<"), LESS_EQUAL("<="), EQUAL("="), GREATER_EQUAL(">="), GREATER_THAN(">");

		public final String sqlRep;
		Modifier(String sqlRep) {
			this.sqlRep = sqlRep;
		}
		public String getSqlRepresentation() {
			return sqlRep;
		}
	}
	
	public enum TimeModifier {
		ANY, NO, FIRST, LAST, MIN, MAX, AVG;
	}

	public enum BooleanOperator {
		AND, OR, NOT;
	}
	
	// probably should combine this with TimeModifier
	public enum GroupMethod {
		ANY, ALL, NONE;
	}
	
	public enum PatientLocationMethod {
		EARLIEST_ENCOUNTER, LATEST_ENCOUNTER, ANY_ENCOUNTER, PATIENT_HEALTH_CENTER
	}
	
}