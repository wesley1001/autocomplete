package com.wesley.autocomplete;

import java.io.FileReader;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.wesley.autocomplete.ACTrieImpl;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader {
	private static Logger logger = LoggerFactory.getLogger("DataLoader.class");
	private static DataLoader instance; 
	private static String dataFile = "C:/Downloads/Softwares/Miscs/products.json";
	private DataLoader(){}
    
    public static DataLoader getInstance(){
        if(instance == null){
            synchronized (DataLoader.class) {
                if(instance == null){
                    instance = new DataLoader();
                }
            }
        }
        return instance;
    }
    
	@SuppressWarnings("unchecked")
	public static void load(){
		try {
			logger.info("loading data from {}", DataLoader.dataFile);
			long startTime = System.currentTimeMillis();
			int totalItems = 0;
			JSONParser parser = new JSONParser();
			JSONArray prods = (JSONArray) parser.parse(new FileReader(DataLoader.dataFile));
			Iterator<JSONObject> iterator = prods.iterator();
			while (iterator.hasNext()) {
				JSONObject prod = (JSONObject) iterator.next();
				String prodName = (String) prod.get("name");
				ACTrieImpl.addProdName(prodName, totalItems);
//				System.out.println("name: " + prodName);
				totalItems ++;
				
			}
//			System.out.println("count:" + totalItems);
			long totalTime = System.currentTimeMillis() - startTime;
			logger.info("load completed, total {} items, spent {} ms", totalItems, totalTime);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

}
