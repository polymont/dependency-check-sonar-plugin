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

import org.sonar.api.web.WidgetProperties;
import org.sonar.api.web.WidgetProperty;
import org.sonar.api.web.WidgetPropertyType;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;

@WidgetProperties({
	@WidgetProperty(key = "enableReportLink", type = WidgetPropertyType.BOOLEAN, defaultValue = "true")
})
public class ZaproxyWidget extends AbstractRubyTemplate implements
		RubyRailsWidget {

	@Override
	public String getId() {
		return "zaproxy";
	}

	@Override
	public String getTitle() {
		return "Known vulnerabilities of website";
	}

	@Override
	protected String getTemplatePath() {
		return "/org/sonar/zaproxy/ui/widget.html.erb";
	}

}
