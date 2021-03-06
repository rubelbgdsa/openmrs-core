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
package org.openmrs.arden;

import java.io.IOException;
import java.io.Writer;

/**
 *
 */
public class Conclude {
	
	private Boolean concludeVal = null;
	
	public Conclude(boolean concludeVal) {
		this.concludeVal = concludeVal;
	}
	
	public String getConcludeVal() {
		String retVal;
		if (concludeVal == true)
			retVal = "true";
		else
			retVal = "false";
		
		return retVal;
	}
	
	public void write(Writer w) {
		try {
			w.append("\t\t\treturn ");
			w.append(getConcludeVal());
			w.append(";\n");
		}
		catch (Exception e) {}
	}
}
