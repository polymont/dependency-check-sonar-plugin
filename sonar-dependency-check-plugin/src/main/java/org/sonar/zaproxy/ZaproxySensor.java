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

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.Rules;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rule.Severity;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.api.utils.log.Profiler;
import org.sonar.commons.OwaspPlugin;
import org.sonar.commons.XmlGlobalReportFile;
import org.sonar.zaproxy.base.ZaproxyConstants;
import org.sonar.zaproxy.base.ZaproxyMetrics;
import org.sonar.zaproxy.base.ZaproxyUtils;
import org.sonar.zaproxy.parser.ZaproxyReportParser;
import org.sonar.zaproxy.parser.element.AlertItem;
import org.sonar.zaproxy.parser.element.OWASPZAPReport;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.io.InputStream;

public class ZaproxySensor implements Sensor {
	
	private static final Logger LOGGER = Loggers.get(ZaproxySensor.class);
	
	private final ZaproxySensorConfiguration configuration;
	private final ResourcePerspectives resourcePerspectives;
	private final FileSystem fileSystem;
	private final ActiveRules activeRules;
	private final XmlGlobalReportFile report;
	private final Rules rules;
	
	private int totalAlerts;
	private int criticalIssuesCount;
	private int majorIssuesCount;
	private int minorIssuesCount;
	private int infoIssuesCount;
	
	public ZaproxySensor(ZaproxySensorConfiguration configuration,
			ResourcePerspectives resourcePerspectives, FileSystem fileSystem,
			ActiveRules activeRules, Rules rules) {
		super();
		this.configuration = configuration;
		this.resourcePerspectives = resourcePerspectives;
		this.fileSystem = fileSystem;
		this.activeRules = activeRules;
		this.rules = rules;
		this.report = new XmlGlobalReportFile(configuration, fileSystem, ZaproxyConstants.TOOL_NAME, 
				ZaproxyConstants.REPORT_PATH_PROPERTY);
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		if(configuration.isEnabled() && this.report.exist()) {
			return true;
		} else {
			return false;
		}
	}
	
	// TODO improve informations show in this part
	private String formatDescription(AlertItem alert) {
		StringBuilder sb = new StringBuilder();
		sb.append("URI: ").append(alert.getUri()).append(" | ");
		sb.append(alert.getDesc());
		return sb.toString();
	}
	
	private void incrementCount(String severity) {
		switch (severity) {
			case Severity.CRITICAL:
				this.criticalIssuesCount++;
				break;
			case Severity.MAJOR:
				this.majorIssuesCount++;
				break;
			case Severity.MINOR:
				this.minorIssuesCount++;
				break;
			case Severity.INFO:
				this.infoIssuesCount++;
				break;
		}
	}
		
	private void addIssue(InputFile inputFile, AlertItem alert) {
		Issuable issuable = this.resourcePerspectives.as(Issuable.class, inputFile);
		if (issuable != null) {
			String severity = ZaproxyUtils.riskCodeToSonarQubeSeverity(alert.getRiskcode());
			String pluginid = String.valueOf(alert.getPluginid());
			// Check if the rule with the pluginid exists
			if( rules.find(RuleKey.of(OwaspPlugin.REPOSITORY_ZAPROXY_KEY, pluginid)) != null ) {
				Issue issue = issuable.newIssueBuilder()
						.ruleKey(RuleKey.of(OwaspPlugin.REPOSITORY_ZAPROXY_KEY, pluginid))
						.message(formatDescription(alert))
						.severity(severity)
						.line(null)
						.build();
				if (issuable.addIssue(issue)) {
					incrementCount(severity);
				}
				
			} else {
				LOGGER.warn("The rule " + OwaspPlugin.REPOSITORY_ZAPROXY_KEY + ":" +pluginid + " doesn't exist.");
			}
		}
	}
	
	private void addIssues(OWASPZAPReport owaspZapReport) {
		if (owaspZapReport.getSite().getAlerts() == null) {
			return;
		}
		for (AlertItem alert : owaspZapReport.getSite().getAlerts()) {
			//InputFile testFile = fileSystem.inputFile(fileSystem.predicates().is(new File(dependency.getFilePath())));

			InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(report.getFile()));
			addIssue(inputFile, alert);
		}
	}
	
	private OWASPZAPReport parseOwaspZapReport() throws IOException, ParserConfigurationException, SAXException {
		try (InputStream stream = this.report.getInputStream()) {
			return new ZaproxyReportParser().parse(stream);
		}
	}

	@Override
	public void analyse(Project project, SensorContext context) {
		Profiler profiler = Profiler.create(LOGGER);
		profiler.startInfo("Process " + ZaproxyConstants.TOOL_NAME + " report");
		try {
			OWASPZAPReport owaspZapReport = parseOwaspZapReport();
			totalAlerts = owaspZapReport.getSite().getAlerts().size();
			addIssues(owaspZapReport);
		} catch (Exception e) {
			throw new RuntimeException("Can not process " + ZaproxyConstants.TOOL_NAME + " report. Ensure the report are located within the project workspace and that sonar.sources is set to reflect these paths (or set sonar.sources=.)", e);
		} finally {
			profiler.stopInfo();
		}
		saveMeasures(context);
	}
	
	private void saveMeasures(SensorContext context) {
		context.saveMeasure(ZaproxyMetrics.HIGH_RISK_LEVEL, (double) criticalIssuesCount);
		context.saveMeasure(ZaproxyMetrics.MEDIUM_RISK_LEVEL, (double) majorIssuesCount);
		context.saveMeasure(ZaproxyMetrics.LOW_RISK_LEVEL, (double) minorIssuesCount);
		context.saveMeasure(ZaproxyMetrics.INFO_RISK_LEVEL, (double) infoIssuesCount);
		context.saveMeasure(ZaproxyMetrics.TOTAL_ALERTS, (double) totalAlerts);
	}

	@Override
	public String toString() {
		return "OWASP ZAProxy";
	}

}
