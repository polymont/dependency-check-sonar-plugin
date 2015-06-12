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
package org.sonar.zaproxy.ui;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ZaproxyWidgetTest {

	@Test
	public void test_rails_template() throws Exception {
		ZaproxyWidget widget = new ZaproxyWidget();
		assertThat(widget.getClass().getResource(widget.getTemplatePath()))
				.as("Template not found: " + widget.getTemplatePath())
				.isNotNull();
	}

	@Test
	public void test_metadata() throws Exception {
		ZaproxyWidget widget = new ZaproxyWidget();
		assertThat(widget.getId()).containsIgnoringCase("zaproxy");
		assertThat(widget.getTitle()).contains("Known Vulnerabilities");
	}

}
