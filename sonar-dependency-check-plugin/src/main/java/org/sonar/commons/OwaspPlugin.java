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

import java.util.Arrays;
import java.util.List;

/**
 * The main/entry class for the sonar plugin.
 * 
 * @author ludovic.roucoux
 *
 */
public final class OwaspPlugin extends SonarPlugin {
	
	public static final String REPOSITORY_KEY = "OWASP";
	public static final String REPOSITORY_ZAPROXY_KEY = "OWASP-ZAPROXY";
	public static final String LANGUAGE_KEY = "neutral";
	public static final String RULE_KEY = "UsingComponentWithKnownVulnerability";

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
