/*
 * OWASP Plugin for SonarQube
 * Copyright (C) 2015 Steve Springett
 * steve.springett@owasp.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.zaproxy.parser.element;

public class OWASPZAPReport {

	private String generated;
	private String versionZAP;
	private Site site;
	
	public OWASPZAPReport(String generated, String versionZAP, Site site) {
		this.generated = generated;
		this.versionZAP = versionZAP;
		this.site = site;
	}
	
	public String getGenerated() {
		return generated;
	}
	
	public String getVersionZAP() {
		return versionZAP;
	}
	
	public Site getSite() {
		return site;
	}
	
	public String toString() {
		String s = "";
		s += "generated : [" + generated + "]\n";
		s += "versionZAP : [" + versionZAP + "]\n";
		s += "site : [" + site + "]\n";
		return s;
	}

}
