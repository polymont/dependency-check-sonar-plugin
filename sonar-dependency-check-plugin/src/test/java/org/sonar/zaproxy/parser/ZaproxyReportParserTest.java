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
package org.sonar.zaproxy.parser;

import org.junit.Test;
import org.sonar.zaproxy.parser.element.AlertItem;
import org.sonar.zaproxy.parser.element.OWASPZAPReport;
import org.sonar.zaproxy.parser.element.Site;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class ZaproxyReportParserTest {

	@Test
	public void parseReport() throws Exception {
		ZaproxyReportParser parser = new ZaproxyReportParser();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("report/zaproxy-report.xml");
		OWASPZAPReport owaspZapReport = parser.parse(inputStream);
		assertThat(owaspZapReport.getGenerated()).isEqualTo("jeu., 7 mai 2015 16:14:12");
		assertThat(owaspZapReport.getVersionZAP()).isEqualTo("2.4.0");
		
		Site site = owaspZapReport.getSite();
		assertThat(site.getHost()).isEqualTo("localhost");
		assertThat(site.getName()).isEqualTo("http://localhost:8180");
		assertThat(site.getPort()).isEqualTo(8180);
		assertThat(site.isSsl()).isEqualTo(false);
		
		Collection<AlertItem> alerts = site.getAlerts();
		assertThat(alerts.size()).isEqualTo(236);
		
		Iterator<AlertItem> iterator = alerts.iterator();
		AlertItem alert = iterator.next();
		
		assertThat(alert.getPluginid()).isEqualTo(10016);
		assertThat(alert.getAlert()).isEqualTo("Web Browser XSS Protection Not Enabled");
		assertThat(alert.getRiskcode()).isEqualTo(1);
		assertThat(alert.getConfidence()).isEqualTo(2);
		assertThat(alert.getRiskdesc()).isEqualTo("Low (Medium)");
		assertThat(alert.getDesc()).isEqualTo("Web Browser XSS Protection is not enabled, or is disabled by the configuration of the 'X-XSS-Protection' HTTP response header on the web server");
		assertThat(alert.getUri()).isEqualTo("http://localhost:8180/robots.txt");
		assertThat(alert.getParam()).isNullOrEmpty();
		assertThat(alert.getAttack()).isNullOrEmpty();
		assertThat(alert.getOtherinfo()).endsWith("Note that this alert is only raised if the response body could potentially contain an XSS payload (with a text-based content type, with a non-zero length).");
		assertThat(alert.getSolution()).isEqualTo("Ensure that the web browser's XSS filter is enabled, by setting the X-XSS-Protection HTTP response header to '1'.");
		assertThat(alert.getReference()).contains("https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet");
		assertThat(alert.getCweid()).isEqualTo(933);
		assertThat(alert.getWascid()).isEqualTo(14);
	}

}
