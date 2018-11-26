package it.sample.rulesengine.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;

@Service
public class RulesCatalogService {

	private static final Logger log = LoggerFactory.getLogger(RulesCatalogService.class);

	// The catalog file defining all the config rules
	// The catalog directory contains .js implemented rules
	public static final String catalogRuleDefaultFile = "E:\\git-repo\\simple-spring-boot-rules-engine\\rules\\rules_catalog.json";
	public static final String catalogRuleDefaultDir = "E:\\git-repo\\simple-spring-boot-rules-engine\\rules\\";
	
	// n-tuple <rule-name , file-name>
	private Map<String, String> fileCatalogMap = new HashMap<String, String>();
	
	// n-tuple <rule-name , rule-text implemented on associated file>
	private Map<String, String> jsCatalogMap = new HashMap<String, String>();

	/**
	 * Load the fileCatalog
	 *
	 * @throws Exception
	 */
	public void loadCatalog(boolean refresh) throws IOException, UnsupportedEncodingException {
		loadCatalog(catalogRuleDefaultFile, refresh);
	}

	/**
	 * Load the catalog file, read rules and cache into rules catalog.
	 *
	 * @param catalogFile
	 * @param isRefresh
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public void loadCatalog(String catalogFile, boolean refresh) throws IOException, UnsupportedEncodingException {

		if (fileCatalogMap == null || fileCatalogMap.isEmpty() || refresh == true) {

			log.debug("Loading rules fileCatalogMap from - " + catalogFile);

			FileSystemResource file = new FileSystemResource(catalogFile);
			
			InputStream inStream = file.getInputStream();

			String json = CharStreams.toString(new InputStreamReader(inStream, "UTF-8"));

			// A "JSON" in the form of "string" can be parsed into a Java Map object
			ObjectMapper mapper = new ObjectMapper();

			fileCatalogMap = mapper.readValue(json, new TypeReference<Map<String, String>>() { });

			log.info("The rules fileCatalogMap - " + fileCatalogMap);

			log.debug("Finished loading rules fileCatalogMap. ");
		}
	}

	public Map<String, String> getFileCatalog() {
		return fileCatalogMap;
	}

	public String getNashornRule(String typeName) throws IOException, UnsupportedEncodingException {
		
		String rule = null;

		// If the catalog map of the rule files is empty, load the catalog file
		if (fileCatalogMap.isEmpty()) {
			loadCatalog(false);
		}

		// Is the given type in the catalog map ?
		if (jsCatalogMap.containsKey(typeName)) {
			// Found it
			rule = jsCatalogMap.get(typeName);
		
		} else {
			// Not found in the catalog map, load it
			// get the rule file for the given type
			rule = getJSRule(typeName);
		}
		
		return rule;
		
	}

	private String getJSRule(String typeName) throws IOException, FileNotFoundException {

		String rulesText = null;
		
		// Get the filename associated to the rule name 
		String ruleFileName = fileCatalogMap.get(typeName);

		if (StringUtils.hasText(ruleFileName)) {
			
			log.debug("Retrieving javascript rule - " + ruleFileName);

			FileSystemResource file = new FileSystemResource(catalogRuleDefaultDir+ruleFileName);
			
			InputStream inStream = file.getInputStream();

			try (BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"))) {
				rulesText = in.lines().collect(Collectors.joining("\n"));
			}

			if (StringUtils.hasText(rulesText)) {
				
				// Found and loaded rules, add to the map
				jsCatalogMap.put(typeName, rulesText);
			
			}
		}

		return rulesText;

	}

}
