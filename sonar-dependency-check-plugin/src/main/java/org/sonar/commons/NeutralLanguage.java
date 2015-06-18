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

import org.sonar.api.resources.AbstractLanguage;

/**
 * In order for a rule repository to work properly, the rules created in the repository
 * must be associated with a language. This is a workaround so that rules that apply to
 * third-party components where the language is not known (or irrelevant) can be used to
 * flag those components as vulnerable.
 *
 * This class simply creates a new 'language' called neutral.
 */
public class NeutralLanguage extends AbstractLanguage {

	public NeutralLanguage() {
		super(OwaspPlugin.LANGUAGE_KEY, OwaspPlugin.LANGUAGE_NAME);
	}

	public String[] getFileSuffixes() {
		return new String[0];
	}

}
