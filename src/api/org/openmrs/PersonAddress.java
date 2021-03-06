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
package org.openmrs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.util.OpenmrsUtil;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * This class is the representation of a person's address. This class is many-to-one to the Person
 * class, so a Person/Patient/User can have zero to n addresses
 */
@Root(strict = false)
public class PersonAddress implements java.io.Serializable, Cloneable, Comparable<PersonAddress> {
	
	private static Log log = LogFactory.getLog(PersonAddress.class);
	
	public static final long serialVersionUID = 343333L;
	
	// Fields
	
	private Integer personAddressId;
	
	private Person person;
	
	private Boolean preferred = false;
	
	private String address1;
	
	private String address2;
	
	private String cityVillage;
	
	private String neighborhoodCell;
	
	private String countyDistrict;
	
	private String townshipDivision;
	
	private String region;
	
	private String subregion;
	
	private String stateProvince;
	
	private String country;
	
	private String postalCode;
	
	private String latitude;
	
	private String longitude;
	
	private User creator;
	
	private Date dateCreated;
	
	private Boolean voided = false;
	
	private User voidedBy;
	
	private Date dateVoided;
	
	private String voidReason;
	
	// Constructors
	
	/** default constructor */
	public PersonAddress() {
	}
	
	/** constructor with id */
	public PersonAddress(Integer personAddressId) {
		this.personAddressId = personAddressId;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "a1:" + getAddress1() + ", a2:" + getAddress2() + ", cv:" + getCityVillage() + ", sp:" + getStateProvince()
		        + ", c:" + getCountry() + ", cd:" + getCountyDistrict() + ", nc:" + getNeighborhoodCell() + ", pc:"
		        + getPostalCode() + ", lat:" + getLatitude() + ", long:" + getLongitude();
	}
	
	/**
	 * Compares this address to the given object/address for similarity. Uses the very basic
	 * comparison of just the PersonAddress.personAddressId
	 * 
	 * @param obj Object (Usually PersonAddress) with which to compare
	 * @return boolean true/false whether or not they are the same objects
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof PersonAddress) {
			PersonAddress p = (PersonAddress) obj;
			if (this.getPersonAddressId() != null && p.getPersonAddressId() != null)
				return (this.getPersonAddressId().equals(p.getPersonAddressId()));
		}
		return false;
	}
	
	/**
	 * Compares this PersonAddress object to the given otherAddress. This method differs from
	 * {@link #equals(Object)} in that this method compares the inner fields of each address for
	 * equality. Note: Null/empty fields on <code>otherAddress</code> /will not/ cause a false value
	 * to be returned
	 * 
	 * @param otherAddress PersonAddress with which to compare
	 * @return boolean true/false whether or not they are the same addresses
	 */
	@SuppressWarnings("unchecked")
	public boolean equalsContent(PersonAddress otherAddress) {
		boolean returnValue = true;
		
		// these are the methods to compare. All are expected to be Strings
		String[] methods = { "getAddress1", "getAddress2", "getCityVillage", "getNeighborhoodCell", "getCountyDistrict",
		        "getTownshipDivision", "getRegion", "getSubregion", "getStateProvince", "getCountry", "getPostalCode",
		        "getLatitude", "getLongitude" };
		
		Class addressClass = this.getClass();
		
		// loop over all of the selected methods and compare this and other
		for (String methodName : methods) {
			try {
				Method method = addressClass.getMethod(methodName, new Class[] {});
				
				String thisValue = (String) method.invoke(this);
				String otherValue = (String) method.invoke(otherAddress);
				
				if (otherValue != null && otherValue.length() > 0)
					returnValue &= otherValue.equals(thisValue);
				
			}
			catch (NoSuchMethodException e) {
				log.warn("No such method for comparison " + methodName, e);
			}
			catch (IllegalAccessException e) {
				log.error("Error while comparing addresses", e);
			}
			catch (InvocationTargetException e) {
				log.error("Error while comparing addresses", e);
			}
			
		}
		
		return returnValue;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.getPersonAddressId() == null)
			return super.hashCode();
		return this.getPersonAddressId().hashCode();
	}
	
	/**
	 * bitwise copy of the personAddress object. NOTICE: THIS WILL NOT COPY THE PATIENT OBJECT. The
	 * PersonAddress.person object in this object AND the cloned object will point at the same
	 * person
	 * 
	 * @return New PersonAddress object
	 */
	public Object clone() {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError("PersonAddress should be cloneable");
		}
	}
	
	/**
	 * @return Returns the address1.
	 */
	@Element(data = true, required = false)
	public String getAddress1() {
		return address1;
	}
	
	/**
	 * @param address1 The address1 to set.
	 */
	@Element(data = true, required = false)
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	/**
	 * @return Returns the address2.
	 */
	@Element(data = true, required = false)
	public String getAddress2() {
		return address2;
	}
	
	/**
	 * @param address2 The address2 to set.
	 */
	@Element(data = true, required = false)
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	/**
	 * @return Returns the cityVillage.
	 */
	@Element(data = true, required = false)
	public String getCityVillage() {
		return cityVillage;
	}
	
	/**
	 * @param cityVillage The cityVillage to set.
	 */
	@Element(data = true, required = false)
	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}
	
	/**
	 * @return Returns the country.
	 */
	@Element(data = true, required = false)
	public String getCountry() {
		return country;
	}
	
	/**
	 * @param country The country to set.
	 */
	@Element(data = true, required = false)
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return Returns the preferred.
	 */
	public Boolean isPreferred() {
		if (preferred == null)
			return new Boolean(false);
		return preferred;
	}
	
	@Attribute(required = true)
	public Boolean getPreferred() {
		return isPreferred();
	}
	
	/**
	 * @param preferred The preferred to set.
	 */
	@Attribute(required = true)
	public void setPreferred(Boolean preferred) {
		this.preferred = preferred;
	}
	
	/**
	 * @return Returns the creator.
	 */
	@Element(required = true)
	public User getCreator() {
		return creator;
	}
	
	/**
	 * @param creator The creator to set.
	 */
	@Element(required = true)
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	/**
	 * @return Returns the dateCreated.
	 */
	@Element(required = true)
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @param dateCreated The dateCreated to set.
	 */
	@Element(required = true)
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	/**
	 * @return Returns the latitude.
	 */
	@Attribute(required = false)
	public String getLatitude() {
		return latitude;
	}
	
	/**
	 * @param latitude The latitude to set.
	 */
	@Attribute(required = false)
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * @return Returns the longitude.
	 */
	@Attribute(required = false)
	public String getLongitude() {
		return longitude;
	}
	
	/**
	 * @param longitude The longitude to set.
	 */
	@Attribute(required = false)
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * @return Returns the person.
	 */
	@Element(required = true)
	public Person getPerson() {
		return person;
	}
	
	/**
	 * @param person The person to set.
	 */
	@Element(required = true)
	public void setPerson(Person person) {
		this.person = person;
	}
	
	/**
	 * @return Returns the personAddressId.
	 */
	@Attribute(required = true)
	public Integer getPersonAddressId() {
		return personAddressId;
	}
	
	/**
	 * @param personAddressId The personAddressId to set.
	 */
	@Attribute(required = true)
	public void setPersonAddressId(Integer personAddressId) {
		this.personAddressId = personAddressId;
	}
	
	/**
	 * @return Returns the postalCode.
	 */
	@Element(data = true, required = false)
	public String getPostalCode() {
		return postalCode;
	}
	
	/**
	 * @param postalCode The postalCode to set.
	 */
	@Element(data = true, required = false)
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * @return Returns the stateProvince.
	 */
	@Element(data = true, required = false)
	public String getStateProvince() {
		return stateProvince;
	}
	
	/**
	 * @param stateProvince The stateProvince to set.
	 */
	@Element(data = true, required = false)
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	
	/**
	 * @return Returns the dateVoided.
	 */
	@Element(required = false)
	public Date getDateVoided() {
		return dateVoided;
	}
	
	/**
	 * @param dateVoided The dateVoided to set.
	 */
	@Element(required = false)
	public void setDateVoided(Date dateVoided) {
		this.dateVoided = dateVoided;
	}
	
	/**
	 * @return Returns the voided.
	 */
	public Boolean isVoided() {
		return voided;
	}
	
	@Attribute(required = true)
	public Boolean getVoided() {
		return isVoided();
	}
	
	/**
	 * @param voided The voided to set.
	 */
	@Attribute(required = true)
	public void setVoided(Boolean voided) {
		this.voided = voided;
	}
	
	/**
	 * @return Returns the voidedBy.
	 */
	@Element(required = false)
	public User getVoidedBy() {
		return voidedBy;
	}
	
	/**
	 * @param voidedBy The voidedBy to set.
	 */
	@Element(required = false)
	public void setVoidedBy(User voidedBy) {
		this.voidedBy = voidedBy;
	}
	
	/**
	 * @return Returns the voidReason.
	 */
	@Element(data = true, required = false)
	public String getVoidReason() {
		return voidReason;
	}
	
	/**
	 * @param voidReason The voidReason to set.
	 */
	@Element(data = true, required = false)
	public void setVoidReason(String voidReason) {
		this.voidReason = voidReason;
	}
	
	/**
	 * @return Returns the countyDistrict.
	 */
	@Element(data = true, required = false)
	public String getCountyDistrict() {
		return countyDistrict;
	}
	
	/**
	 * @param countyDistrict The countyDistrict to set.
	 */
	@Element(data = true, required = false)
	public void setCountyDistrict(String countyDistrict) {
		this.countyDistrict = countyDistrict;
	}
	
	/**
	 * @return Returns the neighborhoodCell.
	 */
	@Element(data = true, required = false)
	public String getNeighborhoodCell() {
		return neighborhoodCell;
	}
	
	/**
	 * @param neighborhoodCell The neighborhoodCell to set.
	 */
	@Element(data = true, required = false)
	public void setNeighborhoodCell(String neighborhoodCell) {
		this.neighborhoodCell = neighborhoodCell;
	}
	
	/**
	 * Convenience method to test whether any of the fields in this address are set
	 * 
	 * @return whether any of the address fields (address1, address2, cityVillage, stateProvince,
	 *         country, countyDistrict, neighborhoodCell, postalCode, latitude, longitude) are
	 *         non-null
	 */
	public boolean isBlank() {
		return getAddress1() == null && getAddress2() == null && getCityVillage() == null && getStateProvince() == null
		        && getCountry() == null && getCountyDistrict() == null && getNeighborhoodCell() == null
		        && getPostalCode() == null && getLatitude() == null && getLongitude() == null;
	}
	
	/**
	 * @return the region
	 */
	@Element(data = true, required = false)
	public String getRegion() {
		return region;
	}
	
	/**
	 * @param region the region to set
	 */
	@Element(data = true, required = false)
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * @return the subregion
	 */
	@Element(data = true, required = false)
	public String getSubregion() {
		return subregion;
	}
	
	/**
	 * @param subregion the subregion to set
	 */
	@Element(data = true, required = false)
	public void setSubregion(String subregion) {
		this.subregion = subregion;
	}
	
	/**
	 * @return the townshipDivision
	 */
	@Element(data = true, required = false)
	public String getTownshipDivision() {
		return townshipDivision;
	}
	
	/**
	 * @param townshipDivision the townshipDivision to set
	 */
	@Element(data = true, required = false)
	public void setTownshipDivision(String townshipDivision) {
		this.townshipDivision = townshipDivision;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(PersonAddress other) {
		int retValue = 0;
		if (other != null) {
			retValue = isVoided().compareTo(other.isVoided());
			if (retValue == 0)
				retValue = other.isPreferred().compareTo(isPreferred());
			if (retValue == 0 && getDateCreated() != null)
				retValue = OpenmrsUtil.compareWithNullAsLatest(getDateCreated(), other.getDateCreated());
			if (retValue == 0)
				retValue = OpenmrsUtil.compareWithNullAsGreatest(getPersonAddressId(), other.getPersonAddressId());
			
			// if we've gotten this far, just check all address values.  If they are
			// equal, leave the objects at 0.  If not, arbitrarily pick retValue=1 
			// and return that (they are not equal).
			if (retValue == 0 && !equalsContent(other))
				retValue = 1;
		}
		return retValue;
	}
}
