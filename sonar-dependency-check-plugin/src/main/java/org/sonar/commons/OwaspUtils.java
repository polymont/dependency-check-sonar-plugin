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
package org.sonar.commons;

import org.codehaus.staxmate.SMInputFactory;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;

public final class OwaspUtils {
	
	private OwaspUtils() {
	}

	public static SMInputFactory newStaxParser() throws FactoryConfigurationError {
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		xmlFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		xmlFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.FALSE);
		xmlFactory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
		xmlFactory.setProperty(XMLInputFactory.IS_VALIDATING, Boolean.FALSE);
		return new SMInputFactory(xmlFactory);
	}
	
}
