package com.wesley.autocomplete;

import java.util.concurrent.ConcurrentHashMap;

public class SuggestionsCache extends AbstractCache {
	private static SuggestionsCache instance;
	private static int AC_CACHE_INIT_CAPACITY = 1000;
	private static int AC_CACHE_MAX_CAPACITY = 10000;
	private static int AC_CACHE_EXPIRE_TIME = 30 * 60 * 1000; // ms
	
	private SuggestionsCache(){
		cacheStore = new ConcurrentHashMap<String, CachedObject>(AC_CACHE_INIT_CAPACITY, 0.9f, 1);
		capacity = AC_CACHE_MAX_CAPACITY;
		expireTime = AC_CACHE_EXPIRE_TIME;
	}
	
	public static SuggestionsCache getInstance(){
        if(instance == null){
            synchronized (SuggestionsCache.class) {
                if(instance == null){
                    instance = new SuggestionsCache();
                }
            }
        }
        return instance;
    }
}
