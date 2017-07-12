package com.wesley.autocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wesley.autocomplete.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/***
 * 
 * @author wuwesley
 * controller for the autocomplete system.
 */
@RestController
@RequestMapping("/")
public class ACController {
	private static Logger logger = LoggerFactory.getLogger("ACController.class");
	private final static SuggestionsCache suggestionsCache = SuggestionsCache.getInstance();	
	private static IAutoComplete storeImpl = null;
	private static ACTrieImpl acTrieImpl = new ACTrieImpl();
	private static ACMemcacheImpl acMemcacheImpl = new ACMemcacheImpl();
	
	
	
	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/search/prefix/{prefix}")
	public List<String> searchSuggestions(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @PathVariable("prefix") String prefix){
		List<String> suggestions = new ArrayList<String>();
		try {
			/* the prefix is null */
			if (null == prefix || prefix.trim() == ""){
				return suggestions;
			}
			logger.trace("Geting request for {} ", prefix);
			/* check the cache first */
			if (suggestionsCache.has(prefix)) {
				logger.trace("Geting suggestions for {} from the cache", prefix);
				suggestions = (List<String>) suggestionsCache.get(prefix);
				return suggestions;
			}
			switch (AutocompleteApplication.getGlobalMode()){
			case "trie":
				storeImpl = acTrieImpl;
				break;
			case "memcache":
				storeImpl = acMemcacheImpl;
				break;
			}
			suggestions = storeImpl.getSuggestions(prefix);
			
			if (suggestions.size() > 0){			
				/* put it into cache */	
				suggestionsCache.put(prefix, suggestions);
			}
			
			
		} catch (ACException e) {
			logger.error(e.getMessage());
		}
		return suggestions;
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/config/get/mode")
	public String getAutocompleteMode(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
		String result = null;
		switch (AutocompleteApplication.getGlobalMode()){
		case "trie":
			result = "trie";
			break;
		case "memcache":
			result = "memcache";
			break;
		}
		return result;
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/config/set/mode/{mode}")
	public String setAutocompleteMode(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @PathVariable("mode") String mode){
		String result = null;
		switch (mode){
		case "trie":
			AutocompleteApplication.setGlobalMode("trie");
			break;
		case "memcache":
			AutocompleteApplication.setGlobalMode("memcache");
			break;
		}
		result = "changed to : " + AutocompleteApplication.getGlobalMode();
		return result;
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/config/set/cache/clear")
	public String clearTheCache(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
		int count = suggestionsCache.count();
		if (count > 0){			
			suggestionsCache.clear();
		}
		String result = "total " +count + " items cleared.";
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, value = "/config/get/cache")
	public Map<String, List<String>> getFromTheCache(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
	   Map<String, List<String>> results = new HashMap<>();
	   for (Entry<String, CachedObject> entry : suggestionsCache.cacheStore.entrySet()) {
		   results.put(entry.getKey(), (List<String>) suggestionsCache.get(entry.getKey()));
	   }
	   return results;
	}

}
