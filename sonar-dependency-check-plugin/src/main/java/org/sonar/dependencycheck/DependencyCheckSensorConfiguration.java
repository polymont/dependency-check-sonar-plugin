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
package org.sonar.dependencycheck;

import org.sonar.commons.CommonsConfiguration;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.BatchExtension;
import org.sonar.api.config.Settings;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.dependencycheck.base.DependencyCheckConstants;


public class DependencyCheckSensorConfiguration implements BatchExtension, CommonsConfiguration {

	private final RulesProfile profile;
	private final Settings settings;

	public DependencyCheckSensorConfiguration(RulesProfile profile, Settings settings) {
		this.profile = profile;
		this.settings = settings;
	}

	public String getReportPath() {
		String reportPath = this.settings.getString(DependencyCheckConstants.REPORT_PATH_PROPERTY);
		if (StringUtils.isBlank(reportPath)) {
			reportPath = "dependency-check-report.xml"; // Setting not specified. Use default filename.
		}
		return reportPath;
	}

}
