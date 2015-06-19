# OWASP Plugin for SonarQube

This plugin try to integrate multiple OWASP tools in one SonarQube plugin. For the moment, Dependency-Check and ZAproxy are integrated.

## Dependency-Check Part

[![Build Status](https://travis-ci.org/stevespringett/dependency-check-sonar-plugin.svg?branch=master)](https://travis-ci.org/stevespringett/dependency-check-sonar-plugin) [ ![Download](https://api.bintray.com/packages/stevespringett/owasp/dependency-check-sonar/images/download.svg) ](https://bintray.com/stevespringett/owasp/dependency-check-sonar/_latestVersion)

Integrates [OWASP Dependency-Check] reports into SonarQube v5.1 or higher. If components being analyzed by Dependency-Check are non-source files (jar, dll, etc), then the value of sonar.import_unknown_files needs to be set to True in the SonarQube configuration.

### About Dependency-Check

Dependency-Check is a utility that attempts to detect publicly disclosed vulnerabilities contained within project dependencies. It does this by determining if there is a Common Platform Enumeration (CPE) identifier for a given dependency. If found, it will generate a report linking to the associated CVE entries.

Dependency-Check supports the identification of project dependencies in a number of different languages including Java, .NET, and Python.

### Screenshots

![alt tag](screenshots/dashboard-widget.png)

### Metrics

The plugin keeps track of a number of statistics including:

* Total number of dependencies scanned
* Total number of vulnerabilities found across all dependencies
* Total number of vulnerable components
* Total number of high, medium, and low severity vulnerabilities

Additionally, the following two metrics are defined:

__Inherited Risk Score (IRS)__

(high * 5) + (medium * 3) + (low * 1)

The IRS is simply a weighted measurement of the vulnerabilities inherited by the 
application through the use of vulnerable components. It does not measure the 
applications actual risk due to those components. The higher the score the more 
risk the application inherits.

__Vulnerable Component Ratio__

(vulnerabilities / vulnerableComponents)

This is simply a measurement of the number of vulnerabilities to the vulnerable 
components (as a percentage). A higher percentage indicates that a large number 
of components contain vulnerabilities. Lower percentages are better.


### Usage

> $ mvn clean package

### Distribution

Ready to use binaries are available from [bintray]. To install, shutdown Sonar and copy the jar to your SonarQube extensions->plugins directory. Then, start Sonar. 

### Ecosystem

Dependency-Check is available as a:
* Command-line utility
* Ant Task
* Gradle Plugin
* Jenkins Plugin
* Maven Plugin
* SonarQube Plugin

NOTE: The Sonar plugin does not generate reports, it reads existing reports. Use one of the other available methods to scan project dependencies and generate the necessary XML report.

### Copyright & License

OWASP Dependency-Check Sonar Plugin is Copyright (c) Steve Springett. All Rights Reserved.

OWASP Dependency-Check is Copyright (c) Jeremy Long. All Rights Reserved.

Permission to modify and redistribute is granted under the terms of the [LGPLv3] license.

[LGPLv3]: http://www.gnu.org/licenses/lgpl.txt
[bintray]: https://bintray.com/stevespringett/owasp/dependency-check-sonar/
[OWASP Dependency-Check]: https://www.owasp.org/index.php/OWASP_Dependency_Check


## ZAProxy Part

Integrates [OWASP Zed Attack Proxy Project] reports into SonarQube v5.1 or higher. If components being analyzed by ZAProxy are non-source files (jar, dll, etc), then the value of sonar.import_unknown_files needs to be set to True in the SonarQube configuration.

[OWASP Zed Attack Proxy Project]: https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project

### About ZAProxy

The Zed Attack Proxy (ZAP) is an easy to use integrated penetration testing tool for finding vulnerabilities in web applications.
It is designed to be used by people with a wide range of security experience and as such is ideal for developers and functional testers who are new to penetration testing.
ZAP provides automated scanners as well as a set of tools that allow you to find security vulnerabilities manually.

### Metrics

The plugin keeps track of a number of statistics including:

* Total number of alerts found
* Total number of high, medium, low and info severity alerts

### Configuration

The value of sonar.zaproxy.reportPath=path/to/myZAPreport.xml needs to be set in SonarQube configuration to get results from this report in SonarQube.

## Commons configuration

The plugin can be entirely disabled with the following SonarQube property :

* sonar.owasp.enabled=false;

It's also possible to disabled only a part of the sensor plugin, with the following SonarQube properties :

* sonar.owasp.dependencycheck.enabled=false (to disable DependencyCheck Sensor);
* sonar.owasp.zaproxy.enabled=false (to disable ZAProxy Sensor);

