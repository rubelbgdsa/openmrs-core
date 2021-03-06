<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Migrate Data" otherwise="/login.htm" redirect="/migration.form"/>

<%@ include file="/WEB-INF/template/header.jsp" %>

<div style="float: right">

	<table><tr valign="top">
	<td style="border: 1px black solid">
		<b><spring:message code="Migration.users"/></b>
		<c:forEach var="item" items="${model.users}">
			<br/>${item}
		</c:forEach>
	</td>
	<td style="border: 1px black solid">
		<b><spring:message code="Migration.locations"/></b>
		<c:forEach var="item" items="${model.locations}">
			<br/>${item}
		</c:forEach>
	</td>
	</tr></table>

</div>

<div style="border: 1px black solid; background: #ffffaa">
	${model.message}
</div>

<hr />
<h3><spring:message code="Migration.hl7Header"/></h3>
<form method=post action="migration.form">
	<input type=hidden name="method" value="runHl7"/>
	<spring:message code="Migration.hl7Filename" text="Filename:"/>
	<input type=text name="filename"/>
	<br/>
	<spring:message code="Migration.pasteHl7Here"/>
	<br/>
	<textarea name="hl7" rows="3" cols="72" wrap="soft"></textarea>
	<br/>
	<input type="submit" value="<spring:message code="Migration.uploadButton"/>" />
</form>

<hr />
<h3>Regimens</h3>
<form method=post action="migration.form">
	<input type=hidden name="method" value="uploadRegimens"/>
	<br/>
	Paste CSV here, not including header. Columns should be
	<pre>patientId,drugName,formulationName,startDate,autoExpireDate,discontinuedDate,discontinuedReason,doseStrength,doseUnit,dosesPerDay,daysPerWeek,prn</pre>
	<br/>
	<textarea name="regimen_csv" rows="3" cols="72" wrap="soft"></textarea>
	<br/>
	<input type="submit" value="<spring:message code="Migration.uploadButton"/>" />
</form>

<hr />
<h3>Relationships, etc</h3>
<form method=post action="migration.form">
	<input type=hidden name="method" value="uploadMigrationFile"/>
	Migration filename:
	<input type=text name="filename"/>
	<br/>
	Automatically create needed users as sources of relationships?
	<select name="auto_create_users">
		<option value="false">No</option>
		<option value="true">Yes</option>
	</select>
	<br/>
	Add automatically-created users to Role with the same name as their relationshipType, if it exists?
	<select name="add_role_when_creating_users">
		<option value="false">No</option>
		<option value="true">Yes</option>
	</select>
	<br/>
	<input type="submit" value="<spring:message code="Migration.uploadButton"/>" />
</form>

<hr />
<h3><spring:message code="Migration.usersHeader"/></h3>
<form method=post action="migration.form">
	<input type=hidden name="method" value="uploadUsers"/>
	<spring:message code="Migration.pasteUserXmlHere"/>
	<br/>
	<textarea name="user_xml" rows="3" cols="72" wrap="soft"></textarea>
	<br/>
	<input type="submit" value="<spring:message code="Migration.uploadButton"/>" />
</form>

<hr />
<h3><spring:message code="Migration.locationsHeader"/></h3>
<form method=post action="migration.form">
	<input type=hidden name="method" value="uploadLocations"/>
	<spring:message code="Migration.pasteLocationXmlHere"/>
	<br/>
	<textarea name="location_xml" rows="3" cols="72" wrap="soft"></textarea>
	<br/>
	<input type="submit" value="<spring:message code="Migration.uploadButton"/>" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %> 