package com.wesley.autocomplete;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class AutocompleteApplication {

	public static void main(String[] args) {
		DataLoader.getInstance();
		DataLoader.load();
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
