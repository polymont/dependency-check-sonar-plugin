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
package org.sonar.zaproxy.rule;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.commons.OwaspPlugin;

public class ZaproxyRuleDefinition implements RulesDefinition {

	private final RulesDefinitionXmlLoader xmlLoader;

	public ZaproxyRuleDefinition(RulesDefinitionXmlLoader xmlLoader) {
		this.xmlLoader = xmlLoader;
	}

	@Override
	public void define(Context context) {
		NewRepository repository = context.createRepository(OwaspPlugin.REPOSITORY_ZAPROXY_KEY, 
				OwaspPlugin.LANGUAGE_KEY).setName(OwaspPlugin.REPOSITORY_ZAPROXY_KEY);
		xmlLoader.load(repository, getClass().getResourceAsStream("/org/sonar/zaproxy/rules.xml"), "UTF-8");
		repository.done();
	}

}
