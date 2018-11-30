package it.sample.rulesengine.controller.rest;

import it.sample.rulesengine.service.RulesCatalogService;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Rest Controller for get rules for the given ruleName at runtime.
 */
@RestController
@RequestMapping("/")
public class RulesController {
	
	private static final Logger log = LoggerFactory.getLogger(RulesController.class);
	
	private static ScriptEngine engine = null;
	
	@Autowired
	private RulesCatalogService catalogService;

	@RequestMapping("/")
	public String home() {
		
		log.info("Rules Engine called.");
		
		return "Rules Engine. <br> Default testing api: <br>"
				+ "http://localhost:8080/api/rules/WhatToDo?family_visiting=yes&money=poor&weather=good <br>"
				+ "http://localhost:8080/api/rules/EligibleTo?age=18 <br>"
				+ "http://localhost:8080/api/rules/test?action=done";
	
	}
	
	@RequestMapping("/refresh")
	public String refresh() throws UnsupportedEncodingException, IOException {
		
		log.info("Refresh catalog Rules Engine.");
		
		catalogService.loadCatalog(true);
		
		return "Refresh.";
	
	}
	
	// TEST RULE:
	//
	// http://localhost:8080/api/rules/WhatToDo?family_visiting=yes&money=poor&weather=good
	// http://localhost:8080/api/rules/test?age=18

	@RequestMapping(value = "/api/rules/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> evaluateRule(@PathVariable String name,
											   @RequestParam MultiValueMap<String, String> params) throws JSONException {
		String json = null;
		
		try {
			
			engine = new ScriptEngineManager().getEngineByName("Nashorn");
			
			String rule = catalogService.getNashornRule(name);
			
			String parameter;
			String value;
			
			for(String s : params.keySet()){

				parameter = s;
				value = params.getFirst(s);
				
				engine.put(parameter,value);
			}
			
			log.info("/"+name+" uri rule, query-string complete: ["+params.toString()+"]");
			
			engine.eval(rule);

			Map<String, String> results = new HashMap<String, String>();
			
			results.put("output", (String) engine.get("output"));
			
			json = (new ObjectMapper()).writeValueAsString(results);
			
			log.info("Output: ["+json+"]");
			
		} catch (Exception e) {
			
			StringBuilder msg = new StringBuilder();
			
			msg.append("Evaluate rule - ").append(name).append(" failed - ");
			
			msg.append(e.getMessage());
			
			log.error(msg.toString());
			
			throw new JSONException(msg.toString());
			
		}
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK);
		
		return responseEntity;
	}
	
	// Nashorn service-call with Context Customization
	
	@RequestMapping(value = "/api/service/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> evaluateContextRule(@PathVariable String name,
											   		  @RequestParam MultiValueMap<String, String> params) throws JSONException {
		String json = null;
		
		try {
			
			engine = new ScriptEngineManager().getEngineByName("Nashorn");
			
			//redirect the javascript print to log
			StringWriter sw = new StringWriter();
			engine.getContext().setWriter(sw);
			
			Bindings b = engine.createBindings();
	        b.put("global", "GLOBAL");
	        
	        engine.setBindings(b, ScriptContext.GLOBAL_SCOPE);

	        b = engine.createBindings();
	       
	        b.put("info", new NashorInfo(engine));
	        b.put("engineVar", "engine");
	        
	        engine.setBindings(b, ScriptContext.ENGINE_SCOPE);

	        engine.eval("newEngineVar='engine';");
	        engine.eval("print('Engine vars:'); for each (var key in info.getEngineScopeKeys()) print (key);");
	        engine.eval("print(); print('Global vars:'); for each (var key in info.getGlobalScopeKeys()) print (key);");    
	        
	        Map<String, String> results = new HashMap<String, String>();
	        
			results.put("output", sw.toString());
			
			json = (new ObjectMapper()).writeValueAsString(results);
			
			log.info("Output: ["+json+"]");

			
		} catch (Exception e) {
			
			StringBuilder msg = new StringBuilder();
			
			msg.append("Evaluate rule - ").append(name).append(" failed - ");
			
			msg.append(e.getMessage());
			
			log.error(msg.toString());
			
			throw new JSONException(msg.toString());
			
		}
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK);
		
		return responseEntity;
	}
	
	
	public class NashorInfo {

        private ScriptEngine scriptEngine;

        public NashorInfo (ScriptEngine scriptEngine) {
            this.scriptEngine = scriptEngine;
        }

        public String[] getEngineScopeKeys() {
            return scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE).keySet().toArray(new String[]{});
        }

        public String[] getGlobalScopeKeys() {
            return scriptEngine.getBindings(ScriptContext.GLOBAL_SCOPE).keySet().toArray(new String[]{});
        }
    }

}


