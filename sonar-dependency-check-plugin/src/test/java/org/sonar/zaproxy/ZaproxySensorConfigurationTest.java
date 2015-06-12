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

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.zaproxy.base.ZaproxyConstants;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZaproxySensorConfigurationTest {
	
	private RulesProfile profile;
	private Settings settings;
	private ZaproxySensorConfiguration sensorConfiguration;

	@Before
	public void init() {
		this.profile = mock(RulesProfile.class);
		this.settings = mock(Settings.class);
		this.sensorConfiguration = new ZaproxySensorConfiguration(this.profile, this.settings);
	}

	@Test
	public void testGetReportPath() {
		when(this.settings.getString(ZaproxyConstants.REPORT_PATH_PROPERTY)).thenReturn("myLocation");
		assertThat(this.sensorConfiguration.getReportPath()).isEqualTo("myLocation");
	}

}
