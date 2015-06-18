/*
 * Dependency-Check Plugin for SonarQube
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
package org.sonar.zaproxy.parser;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.commons.NeutralProfile;

import org.sonar.commons.OwaspUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.staxmate.SMInputFactory;
import org.codehaus.staxmate.in.SMHierarchicCursor;
import org.codehaus.staxmate.in.SMInputCursor;
import org.sonar.dependencycheck.base.DependencyCheckUtils;
import org.sonar.zaproxy.parser.element.AlertItem;
import org.sonar.zaproxy.parser.element.OWASPZAPReport;
import org.sonar.zaproxy.parser.element.Site;

import javax.xml.stream.XMLStreamException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class ZaproxyReportParser {
	
	private static final Logger LOGGER = Loggers.get(ZaproxyReportParser.class);
	
	public OWASPZAPReport parse(InputStream inputStream) {
		SMInputFactory inputFactory = OwaspUtils.newStaxParser();
		try {
			SMHierarchicCursor rootC = inputFactory.rootElementCursor(inputStream);
			
			SMInputCursor owaspZapReportCursor = rootC.advance(); // <OWASPZAPReport>
			
			String gererated = owaspZapReportCursor.getAttrValue("generated");
			String versionZAP = owaspZapReportCursor.getAttrValue("version");

			SMInputCursor childCursor = rootC.childCursor(); // Child of <OWASPZAPReport>, here only <site>

			Site site = null;

			while (childCursor.getNext() != null) {
				String nodeName = childCursor.getLocalName();
				if ("site".equals(nodeName)) {
					site = processSite(childCursor);
				}
			}
			return new OWASPZAPReport(gererated, versionZAP, site);
		} catch (XMLStreamException e) {
			throw new IllegalStateException("XML is not valid", e);
		}
	}
	
	private Site processSite(SMInputCursor siteCursor) throws XMLStreamException {
		Site site = new Site();
		
		site.setHost(siteCursor.getAttrValue("host"));
		site.setName(siteCursor.getAttrValue("name"));
		site.setPort(Integer.valueOf(siteCursor.getAttrValue("port")));
		site.setSsl(Boolean.valueOf(siteCursor.getAttrValue("ssl")));
		
		SMInputCursor childCursor = siteCursor.childCursor(); // Child of <site>, here only <alerts>
		
		while (childCursor.getNext() != null) {
			String nodeName = childCursor.getLocalName();
			if ("alerts".equals(nodeName)) {
				site.setAlerts(processAlerts(childCursor));
			}
		}
		return site;
	}
	
	private Collection<AlertItem> processAlerts(SMInputCursor alertsCursor) throws XMLStreamException {
		Collection<AlertItem> alertItemCollection = new ArrayList<AlertItem>();
		SMInputCursor alertItemCursor = alertsCursor.childElementCursor("alertitem");

		while (alertItemCursor.getNext() != null) {
			alertItemCollection.add(processAlertItem(alertItemCursor));
		}
		return alertItemCollection;
	}
	
	private AlertItem processAlertItem(SMInputCursor alertItemCursor) throws XMLStreamException {
		AlertItem alertItem = new AlertItem();
		SMInputCursor childCursor = alertItemCursor.childCursor();
		while (childCursor.getNext() != null) {
			String nodeName = childCursor.getLocalName();
			if ("pluginid".equals(nodeName)) {
				alertItem.setPluginid(childCursor.getElemIntValue());
			} else if ("alert".equals(nodeName)) {
				alertItem.setAlert(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("riskcode".equals(nodeName)) {
				alertItem.setRiskcode(childCursor.getElemIntValue());
			} else if ("confidence".equals(nodeName)) {
				alertItem.setConfidence(childCursor.getElemIntValue());
			} else if ("riskdesc".equals(nodeName)) {
				alertItem.setRiskdesc(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("desc".equals(nodeName)) {
				alertItem.setDesc(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("uri".equals(nodeName)) {
				alertItem.setUri(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("param".equals(nodeName)) {
				alertItem.setParam(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("attack".equals(nodeName)) {
				alertItem.setAttack(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("evidence".equals(nodeName)) {
				alertItem.setEvidence(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("otherinfo".equals(nodeName)) {
				alertItem.setOtherinfo(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("solution".equals(nodeName)) {
				alertItem.setSolution(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("reference".equals(nodeName)) {
				alertItem.setReference(StringUtils.trim(childCursor.collectDescendantText(false)));
			} else if ("cweid".equals(nodeName)) {
				alertItem.setCweid(childCursor.getElemIntValue());
			} else if ("wascid".equals(nodeName)) {
				alertItem.setWascid(childCursor.getElemIntValue());
			}
		}
		return alertItem;
	}

}
