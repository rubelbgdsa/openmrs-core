<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Identifier Types" otherwise="/login.htm" redirect="/admin/patients/patientIdentifierType.form" />

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><spring:message code="PatientIdentifierType.title"/></h2>

<form method="post">
<table>
	<tr>
		<td><spring:message code="general.name"/></td>
		<td>
			<spring:bind path="patientIdentifierType.name">
				<input type="text" name="name" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td valign="top"><spring:message code="general.description"/></td>
		<td valign="top">
			<spring:bind path="patientIdentifierType.description">
				<textarea name="description" rows="3" cols="40">${status.value}</textarea>
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td><spring:message code="PatientIdentifierType.format"/></td>
		<td>
			<spring:bind path="patientIdentifierType.format">
				<input type="text" name="format" value="${status.value}" size="35" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
	<td><spring:message code="PatientIdentifierType.formatDescription"/></td>
		<td>
			<spring:bind path="patientIdentifierType.formatDescription">
				<input type="text" name="${status.expression}" value="${fn:replace(status.value, "\"", "&quot;")}" size="50" />
				<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
			</spring:bind>
		</td>
	</tr>
	<tr>
		<td><spring:message code="PatientIdentifierType.required" /></td>
		<td><spring:bind path="patientIdentifierType.required">
			<input type="hidden" name="_${status.expression}">
			<input type="checkbox" name="${status.expression}" value="true"
				<c:if test="${status.value == true}">checked</c:if> />
			<c:if test="${status.errorMessage != ''}">
				<span class="error">${status.errorMessage}</span>
			</c:if>
		</spring:bind></td>
	</tr>
	<tr>
		<td><spring:message code="PatientIdentifierType.validator" /></td>
		<td><spring:bind path="patientIdentifierType.validator">
			<select name="${status.expression}">
				<option value="">None</option>
				<c:forEach var="piv" items="${patientIdentifierValidators}">
					<option value="${piv.class.name}" 
						<c:if test="${status.value == piv.class.name}">selected</c:if> 
					/>
					${piv.name}
						<c:if test="${defaultValidatorName == piv.name}"> (default)</c:if>
					</option>
				</c:forEach>
			</select>
			<c:if test="${status.errorMessage != ''}">
				<span class="error">${status.errorMessage}</span>
			</c:if>
		</spring:bind></td>
	</tr>
	<c:if test="${!(patientIdentifierType.creator == null)}">
		<tr>
			<td><spring:message code="general.createdBy" /></td>
			<td>
				${patientIdentifierType.creator.personName} -
				<openmrs:formatDate date="${patientIdentifierType.dateCreated}" type="long" />
			</td>
		</tr>
	</c:if>
</table>
<input type="hidden" name="patientIdentifierTypeId:int" value="${patientIdentifierType.patientIdentifierTypeId}">
<br />
<input type="submit" value="<spring:message code="PatientIdentifierType.save"/>">
</form>

<script type="text/javascript">
 document.forms[0].elements[0].focus();
</script>

<%@ include file="/WEB-INF/template/footer.jsp" %>