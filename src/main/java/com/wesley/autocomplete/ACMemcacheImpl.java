package com.wesley.autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ACMemcacheImpl implements IAutoComplete{
	private static Logger logger = LoggerFactory.getLogger("ACMemcacheImpl.class");
	private static final Object lock = new Object();
	private static final int MAX_INDEXED_CHARS = 20;
//	private static List<String> stopWords = DataLoader.stopWords;
//	public static MemcacheService productIndex = MemcacheServiceFactory.getMemcacheService("productIndex");
//	public static MemcacheService productName = MemcacheServiceFactory.getMemcacheService("productName");
	public static Map<String, String> productIndex = new HashMap<>();
	public static Map<String, String> productName = new HashMap<>();
	
	public ACMemcacheImpl(){}
	
	public void addProdName(String prodName, int order) {
        String orderStr = Integer.toString(order);
		synchronized (lock) {
            ACEntity prod = ACEntity.fromFormattedString(prodName);
            if (prod != null) {
            	productName.put(orderStr,prod.getKeyword());
            	/* add the index */
            	addIndex(prod.getKeyword(), order);

            }

       }
    }
	
	public List<String> getSuggestions(String prefix){
    	List<String> ret = new ArrayList<String>();
    	if (prefix.trim() == "") {
           return ret;
        }
    	String finalWords = prefix.replaceAll("\\s","").toLowerCase();
        
        if (Utils.isValid(finalWords) ) {
            Map<String, ACEntity> map = getMatchedProds(finalWords);
            List<ACEntity> list = new ArrayList<ACEntity>(map.values());
            Collections.sort(list, new Comparator<ACEntity>() {
                @Override
                public int compare(ACEntity ps0, ACEntity ps1) {
                    return Integer.compare(ps0.order, ps1.order);
                }
            });
            for (ACEntity suggestion : list) {
                ret.add(suggestion.getKeyword());
                if (ret.size() >= 9) {
                    break;
                }
            }
        }
        return ret;
    }
	
	public void addIndex(String productName, int order) {
		if (productName.trim() == ""){
			return;
		}
		String finalWords = productName.replaceAll("\\s","").toLowerCase();
		/* create the index */
		for (int i = 0; i < finalWords.length(); i++) {
			if (i > MAX_INDEXED_CHARS){break;}
			String keyword = finalWords.substring(0, i+1);
			/* get current index from memcache */
			String indexExists = (String) productIndex.get(keyword);				  
			String newIndex = insertIdToContent(indexExists, order);
			productIndex.put(keyword, newIndex);
		}		
	}
		
	public Map<String, ACEntity> getMatchedProds(String keyword){
		Map<String, ACEntity> results = new HashMap<String, ACEntity>();
		/* get the current index for the key words */
		String indexExists = (String) productIndex.get(keyword);
		if (null == indexExists){return results;}
		/* convert the string to list */
		List<String> prodOrders = Arrays.asList(indexExists.split(","));
		for(String key:prodOrders){
			System.out.println("find key:" + key);
			results.put(key, new ACEntity(productName.get(key)));
		}
//		/* get the matched product */
//		Map<String, Object> prodsMap = productName.getAll(prodOrders);
//		for (Map.Entry<String, Object> entry : prodsMap.entrySet())
//		{
//			System.out.println(entry.getKey() + "/" + entry.getValue());
//			results.put(entry.getKey(), new ACEntity(entry.getKey().toString()));
//		}
		return results;
		
    }
	
	public String insertIdToContent(String currentIndex, int newOrder){
		String newOrderStr = Integer.toString(newOrder);
		if (null ==  currentIndex || currentIndex.trim() == ""){
			return newOrderStr;
		}
    	List<String> prodOrders = Arrays.asList(currentIndex.split(","));
    	if (prodOrders.contains(newOrderStr)){
    		return currentIndex;
    	}else{
    		return currentIndex + "," + newOrderStr;
    	}
    }

	@Override
	public String getImplName() {
		// TODO Auto-generated method stub
		return "memcache";
	}
	
}
