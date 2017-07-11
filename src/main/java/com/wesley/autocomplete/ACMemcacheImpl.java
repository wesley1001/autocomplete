package com.wesley.autocomplete;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ACMemcacheImpl {
	private static final int MIN_INDEXED_CHAR = 1;
	private static final int MAX_INDEXED_CHAR = 15;
	private MemcacheService ngramIndex = MemcacheServiceFactory.getMemcacheService("ngramIndex");
	private MemcacheService products = MemcacheServiceFactory.getMemcacheService("products");
	private void addIndex(String key, int order) {
		
	}
	
}
