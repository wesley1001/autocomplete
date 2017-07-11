package com.wesley.autocomplete;

import java.util.ArrayList;
import java.util.List;

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
	
			suggestions = ACTrieImpl.getSuggestions(prefix);
			
			if (suggestions.size() > 0){			
				/* put it into cache */	
				suggestionsCache.put(prefix, suggestions);
			}
			
			
		} catch (ACException e) {
			logger.error(e.getMessage());
		}
		return suggestions;
	}

}
