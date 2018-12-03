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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;

@Service
public class RulesCatalogService {

	private static final Logger log = LoggerFactory.getLogger(RulesCatalogService.class);


	/* Pay attention!
	 * Retrieve application.yml or application.properties variable using @Value 
	 *  - it always return null if use static keywords, 
	 *  - if try to assign value on initial instance phase, like: String file_path=catalogRuleDefaultDir+catalogRuleDefaultFileName
	 **/
	
	@Value("${files.catalog-rule.dir-path}")
	private String catalogRuleDefaultDir;
	
	@Value("${files.catalog-rule.name}")
	private String catalogRuleDefaultFileName;
	
	// n-tuple <rule-name , .js file-name>
	private Map<String, String> fileCatalogMap = new HashMap<String, String>();
	
	// n-tuple <rule-name , full rule-text>
	private Map<String, String> jsCatalogMap = new HashMap<String, String>();

	/**
	 * To prepare and call the fileCatalogMap load
	 * 
	 * @throws Exception
	 */
	public void loadCatalog(boolean refresh) throws IOException, UnsupportedEncodingException {	
		String __CATALOG_RULE_FILE__ = catalogRuleDefaultDir.concat(catalogRuleDefaultFileName);
		loadCatalog(__CATALOG_RULE_FILE__, refresh);		
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

			log.info("Loading rules fileCatalogMap from - " + catalogFile);

			FileSystemResource file = new FileSystemResource(catalogFile);
			
			InputStream inStream = file.getInputStream();

			String json = CharStreams.toString(new InputStreamReader(inStream, "UTF-8"));

			// A "json" in the form of "string" can be parsed into a Java Map Object
			ObjectMapper mapper = new ObjectMapper();

			fileCatalogMap = mapper.readValue(json, new TypeReference<Map<String, String>>() { });

			log.info("The rules fileCatalogMap - " + fileCatalogMap);

			log.info("Finished loading rules fileCatalogMap. ");
		}
	}

	/**
	 * Getter method 
	 * 
	 * @return
	 */
	public Map<String, String> getFileCatalog() {
		return fileCatalogMap;
	}

	/**
	 * To obtain the full rule-text, ready to be performed on Nashorn engine
	 * 
	 * @param ruleName
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public String getNashornRule(String ruleName) throws IOException, UnsupportedEncodingException {
		
		String rule = null;

		// If the catalog map of the rule files is empty, load the catalog file
		if (fileCatalogMap.isEmpty()) {
			loadCatalog(false);
		}

		// Is the given rule name in the catalog map ?
		if (jsCatalogMap.containsKey(ruleName)) {
			// Found it
			rule = jsCatalogMap.get(ruleName);
		
		} else {
			// Not found in the catalog map, load it
			// get the rule file for the given type
			rule = getAndLoadJSRule(ruleName);
		}
		
		return rule;
		
	}

	/**
	 * 
	 * To obtain the content and contextual put on map the full rule-text.
	 * 
	 * @param ruleName
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private String getAndLoadJSRule(String ruleName) throws IOException, FileNotFoundException {

		String rulesText = null;
		
		// Get the filename of .js associated to the rule name 
		String jsRuleFileName = fileCatalogMap.get(ruleName);

		if (StringUtils.hasText(jsRuleFileName)) {
			
			log.info("Retrieving javascript rule - " + jsRuleFileName);

			FileSystemResource file = new FileSystemResource(catalogRuleDefaultDir+jsRuleFileName);
			
			InputStream inStream = file.getInputStream();

			try (BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"))) {
				rulesText = in.lines().collect(Collectors.joining("\n"));
			}

			if (StringUtils.hasText(rulesText)) {
				// Found and loaded rules, add to the map
				jsCatalogMap.put(ruleName, rulesText);
			}
		
		}

		return rulesText;

	}

}
