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
package org.sonar.zaproxy.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.sonar.api.rule.Severity;

import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ZaproxyUtilsTest {
	
	private final int riskCodeSeverity;
	private final String expectedSeverity;
	
	public ZaproxyUtilsTest(int riskCodeSeverity, String expectedSeverity) {
		this.riskCodeSeverity = riskCodeSeverity;
		this.expectedSeverity = expectedSeverity;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> severities() {
		return Arrays.asList(new Object[][]{
				{3, Severity.CRITICAL},
				{2, Severity.MAJOR},
				{1, Severity.MINOR},
				{0, Severity.INFO}
		});
	}

	@Test
	public void testRiskCodeToSonarQubeSeverity() {
		assertThat(ZaproxyUtils.riskCodeToSonarQubeSeverity(this.riskCodeSeverity)).isEqualTo(this.expectedSeverity);
	}

}
