package com.wesley.autocomplete;


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
		SpringApplication.run(AutocompleteApplication.class, args);
	}
}
