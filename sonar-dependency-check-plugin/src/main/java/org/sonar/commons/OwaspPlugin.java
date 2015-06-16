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

import org.sonar.zaproxy.base.ZaproxyConstants;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.dependencycheck.DependencyCheckSensor;
import org.sonar.dependencycheck.DependencyCheckSensorConfiguration;
import org.sonar.dependencycheck.base.DependencyCheckMetrics;
import org.sonar.dependencycheck.rule.KnownCveRuleDefinition;
import org.sonar.dependencycheck.ui.DependencyCheckWidget;
import org.sonar.zaproxy.ZaproxySensor;
import org.sonar.zaproxy.ZaproxySensorConfiguration;
import org.sonar.zaproxy.base.ZaproxyMetrics;
import org.sonar.zaproxy.rule.ZaproxyRuleDefinition;
import org.sonar.zaproxy.ui.ZaproxyWidget;
import org.sonar.dependencycheck.base.DependencyCheckConstants;

import java.util.Arrays;
import java.util.List;

/**
 * The main/entry class for the sonar plugin.
 * 
 * @author ludovic.roucoux
 *
 */
@Properties({
	@Property(
		key = OwaspPlugin.ENABLED,
		defaultValue = "true",
		name = "Activation of this OWASP plugin",
		description = "This property can be set to false in order to deactivate the OWASP plugin.",
		module = true,
		project = true,
		global = true,
		type = PropertyType.BOOLEAN
	),
	@Property(
		key = ZaproxyConstants.ENABLED_ZAPROXY,
		defaultValue = "true",
		name = "Activation of the ZAProxy sensor",
		description = "This property can be set to false in order to deactivate the ZAProxy sensor.",
		module = true,
		project = true,
		global = true,
		type = PropertyType.BOOLEAN
	),
	@Property(
		key = DependencyCheckConstants.ENABLED_DEPENDENCY_CHECK,
		defaultValue = "true",
		name = "Activation of the DependencyCheck sensor",
		description = "This property can be set to false in order to deactivate the DependencyCheck sensor.",
		module = true,
		project = true,
		global = true,
		type = PropertyType.BOOLEAN
	),
	@Property(
		key = ZaproxyConstants.RULES_FILE_PATH_PROPERTY,
		name = "Path to file",
		description = "Path to file containing all rules used by ZAProxy. The Sonar Server must be restart.",
		module = true,
		project = true,
		global = true,
		type = PropertyType.STRING
	)})
public final class OwaspPlugin extends SonarPlugin {
	
	public static final String REPOSITORY_ZAPROXY_KEY = "OWASP-ZAPROXY";
	public static final String REPOSITORY_DEPENDENCY_CHECK_KEY = "OWASP-DEPENDENCY-CHECK";
	public static final String LANGUAGE_KEY = "neutral";
	public static final String RULE_KEY = "UsingComponentWithKnownVulnerability";
	
	public static final String ENABLED = "sonar.owasp.enabled";

	@Override
	public List getExtensions() {
		return Arrays.asList(
				// Commons
				NeutralProfile.class,
				NeutralLanguage.class,				
				
				// DependencyCheck
				DependencyCheckSensor.class,
				DependencyCheckSensorConfiguration.class,
				DependencyCheckMetrics.class,
				KnownCveRuleDefinition.class,
				DependencyCheckWidget.class,
				
				// Zaproxy
				ZaproxySensor.class,
				ZaproxySensorConfiguration.class,
				ZaproxyMetrics.class,
				ZaproxyRuleDefinition.class,
				ZaproxyWidget.class
				);
	}
	
	
	

}
