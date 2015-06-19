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

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public final class ZaproxyMetrics implements Metrics {

	public static final String DOMAIN = "OWASP-ZAProxy";

	public static final String HIGH_RISK_LEVEL_KEY = "high_risk_level";
	public static final String MEDIUM_RISK_LEVEL_KEY = "medium_risk_level";
	public static final String LOW_RISK_LEVEL_KEY = "low_risk_level";
	public static final String INFO_RISK_LEVEL_KEY = "info_risk_level";
	public static final String TOTAL_ALERTS_KEY = "total_alerts";

	public static final Metric HIGH_RISK_LEVEL = new Metric.Builder(HIGH_RISK_LEVEL_KEY, "High Risk Level Alerts", Metric.ValueType.INT)
			.setDescription("High Risk Level Alerts")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(ZaproxyMetrics.DOMAIN)
			.setBestValue(0.0)
			.setHidden(false)
			.create();

	public static final Metric MEDIUM_RISK_LEVEL = new Metric.Builder(MEDIUM_RISK_LEVEL_KEY, "Medium Risk Level Alerts", Metric.ValueType.INT)
			.setDescription("Medium Risk Level Alerts")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(ZaproxyMetrics.DOMAIN)
			.setBestValue(0.0)
			.setHidden(false)
			.create();

	public static final Metric LOW_RISK_LEVEL = new Metric.Builder(LOW_RISK_LEVEL_KEY, "Low Risk Level Alerts", Metric.ValueType.INT)
			.setDescription("Low Risk Level Alerts")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(ZaproxyMetrics.DOMAIN)
			.setBestValue(0.0)
			.setHidden(false)
			.create();

	public static final Metric INFO_RISK_LEVEL = new Metric.Builder(INFO_RISK_LEVEL_KEY, "Info Risk Level Alerts", Metric.ValueType.INT)
			.setDescription("Info Risk Level Alerts")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(ZaproxyMetrics.DOMAIN)
			.setBestValue(0.0)
			.setHidden(false)
			.create();

	public static final Metric TOTAL_ALERTS = new Metric.Builder(TOTAL_ALERTS_KEY, "Total Alerts", Metric.ValueType.INT)
			.setDescription("Total Alerts")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(false)
			.setDomain(ZaproxyMetrics.DOMAIN)
			.setBestValue(0.0)
			.setHidden(false)
			.create();

	@Override
	public List<Metric> getMetrics() {
		return Arrays.asList(				
				ZaproxyMetrics.HIGH_RISK_LEVEL,
				ZaproxyMetrics.MEDIUM_RISK_LEVEL,
				ZaproxyMetrics.LOW_RISK_LEVEL,
				ZaproxyMetrics.INFO_RISK_LEVEL,
				ZaproxyMetrics.TOTAL_ALERTS
		);
	}

}
