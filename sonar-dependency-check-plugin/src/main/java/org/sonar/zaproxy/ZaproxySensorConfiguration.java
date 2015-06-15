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
package org.sonar.zaproxy;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.BatchExtension;
import org.sonar.api.config.Settings;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.commons.CommonsConfiguration;
import org.sonar.commons.OwaspPlugin;
import org.sonar.zaproxy.base.ZaproxyConstants;

public class ZaproxySensorConfiguration implements BatchExtension, CommonsConfiguration {
	
	private final RulesProfile profile;
	private final Settings settings;

	public ZaproxySensorConfiguration(RulesProfile profile, Settings settings) {
		this.profile = profile;
		this.settings = settings;
	}

	@Override
	public String getReportPath() {
		String reportPath = this.settings.getString(ZaproxyConstants.REPORT_PATH_PROPERTY);
		if (StringUtils.isBlank(reportPath)) {
			reportPath = "zaproxy-report.xml"; // Setting not specified. Use default filename.
		}
		return reportPath;
	}

	@Override
	public boolean isEnabled() {
		if(settings.getBoolean(OwaspPlugin.ENABLED)
				&& settings.getBoolean(ZaproxyConstants.ENABLED_ZAPROXY)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getRulesFilePath() {
		String rulesFilePath = this.settings.getString(ZaproxyConstants.RULES_FILE_PATH_PROPERTY);
		if (StringUtils.isBlank(rulesFilePath)) {
			rulesFilePath = "rules.xml"; // Setting not specified. Use default filename.
		}
		return rulesFilePath;
	}

}
