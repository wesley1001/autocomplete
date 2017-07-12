package com.wesley.autocomplete;


import static org.mockito.Matchers.intThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class AutocompleteApplication {
	/* mode : trie, memcache */
	private static String globalMode = "trie";
	
	public static String getGlobalMode() {
		return globalMode;
	}

	public static void setGlobalMode(String globalMode) {
		AutocompleteApplication.globalMode = globalMode;
	}

	public static void main(String[] args) {
		DataLoader.getInstance();
		ACTrieImpl acTrieImpl = new ACTrieImpl();
		DataLoader.load(acTrieImpl);
		ACMemcacheImpl acMemcacheImpl = new ACMemcacheImpl();
		DataLoader.load(acMemcacheImpl);
//		ACMemcacheImpl acMemcacheImpl = new ACMemcacheImpl();
//		String productName1 = "Hal Leonard - Various Composers: Gospel's Greatest Sheet Music - Multi";
//		int order1 = 1;
//		acMemcacheImpl.addProdName(productName1, order1);
//		String productName2 = "Bell'O - Triple Play TV Stand for Flat-Panel TVs Up to 48\" - Apple";
//		int order2 = 2;
//		acMemcacheImpl.addProdName(productName2, order2);
//		String productName3 = "Bell'O - Triple Play TV Stand for Flat-Panel TVs Up to 46\" - Cherry";
//		int order3 = 3;
//		acMemcacheImpl.addProdName(productName3, order3);
//		String productName4 = "TRU - Crossover Single-Serve/10-Cup Coffeemaker - Black";
//		int order4 = 4;
//		acMemcacheImpl.addProdName(productName4, order4);
//		String productName5 = "Urban Armor Gear - Composite Case for Apple® iPhone® 6 and 6s - White/Black";
//		int order5 = 5;
//		acMemcacheImpl.addProdName(productName5, order5);
//		System.out.println("product:");
//		for (Map.Entry<String, String> entry : ACMemcacheImpl.productName.entrySet())
//		{
//			System.out.println(entry.getKey() + "/" + entry.getValue());
//		}
//		System.out.println("index:");
//		for (Map.Entry<String, String> entry : ACMemcacheImpl.productIndex.entrySet())
//		{
//			System.out.println(entry.getKey() + "/" + entry.getValue());
//		}
//		List<String> ss = acMemcacheImpl.getSuggestions("urban armor");
//		for(String s:ss){
//			System.out.println("suggestion:" + ss);
//		}
//		List<String> suggestions = ACTrieImpl.getSuggestions("ab");
//		for (String prodName: suggestions){
//			System.out.println(prodName);
//		}
//		try {
//			suggestionsCache.put("ab", suggestions);
//		} catch (ACException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<String> csugs = (List<String>) suggestionsCache.get("ab");
//		for (String prodName: csugs){
//			System.out.println(prodName);
//		}
		SpringApplication.run(AutocompleteApplication.class, args);
	}
}
