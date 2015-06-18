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
package org.sonar.commons;

import org.apache.commons.lang.StringUtils;
import org.codehaus.staxmate.SMInputFactory;
import org.codehaus.staxmate.in.SMHierarchicCursor;
import org.codehaus.staxmate.in.SMInputCursor;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import javax.xml.stream.XMLStreamException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NeutralProfile extends ProfileDefinition {

	private final XMLProfileParser xmlParser;
	private final RuleFinder ruleFinder;
	
	private static final Logger LOGGER = Loggers.get(NeutralProfile.class);

	public NeutralProfile(RuleFinder ruleFinder, XMLProfileParser xmlParser) {
		this.xmlParser = xmlParser;
		this.ruleFinder = ruleFinder;
	}
	
	/**
	 * Return the key of the rule pointed by ruleCursor.
	 * 
	 * @param ruleCursor
	 * @return the rule's key
	 * @throws XMLStreamException
	 */
	private String processKey(SMInputCursor ruleCursor) throws XMLStreamException {
		SMInputCursor childCursor = ruleCursor.childElementCursor("key");
		childCursor.getNext();
		return StringUtils.trim(childCursor.collectDescendantText(false));
	}
	
	/**
	 * Process all rules to get all rules' key.
	 * 
	 * @param rulesCursor
	 * @return a collection of all keys.
	 * @throws XMLStreamException
	 */
	private Collection<String> processRules(SMInputCursor rulesCursor) throws XMLStreamException {
		Collection<String> ruleKeysCollection = new ArrayList<String>();
		
		SMInputCursor ruleCursor = rulesCursor.childElementCursor("rule"); // Child of <rules>, here <rule>
		
		// Process all <rule> node
		while (ruleCursor.getNext() != null) {
			ruleKeysCollection.add(processKey(ruleCursor));
		}
		
		return ruleKeysCollection;
	}
	
	private Collection<String> getAllRuleKeysFromFile(InputStream inputStream) {
		SMInputFactory inputFactory = OwaspUtils.newStaxParser();
		try {
			SMHierarchicCursor rootC = inputFactory.rootElementCursor(inputStream); 
			SMInputCursor rulesCursor = rootC.advance(); // <rules>
			
			return processRules(rulesCursor);
		} catch (XMLStreamException e) {
			throw new IllegalStateException("XML is not valid", e);
		}
	}

	@Override
	public RulesProfile createProfile(ValidationMessages validation) {
		
		// To load profile's rules from a file
		/*return xmlParser.parse(new InputStreamReader(
				getClass().getResourceAsStream("/org/sonar/dependencycheck/profile.xml")), validation);*/
		
		// To create a neutral profile, the file "rules.xml" is covered to collect all rules's keys
		// After that, all rules with corresponding key are enabled in the profile.
		
		RulesProfile profile = RulesProfile.create(OwaspPlugin.LANGUAGE_NAME, OwaspPlugin.LANGUAGE_KEY);
		
		InputStream inputStream = getClass().getResourceAsStream(OwaspPlugin.RESOURCES_ZAPROXY_RULES_FILES);		
		
		List<String> ruleKeysList = new ArrayList<String>(getAllRuleKeysFromFile(inputStream));
		
		for (String ruleKey : ruleKeysList) {
			Rule rule = ruleFinder.findByKey(OwaspPlugin.REPOSITORY_ZAPROXY_KEY, ruleKey);
			profile.activateRule(rule, null);
		}
		
		// Add the dependency check rule to the profile
		Rule ruleDependencyCheck = ruleFinder.findByKey(OwaspPlugin.REPOSITORY_DEPENDENCY_CHECK_KEY, OwaspPlugin.RULE_KEY);
		profile.activateRule(ruleDependencyCheck, null);
		
		profile.setDefaultProfile(true);
		return profile;
	}
}
