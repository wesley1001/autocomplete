package com.wesley.autocomplete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader {
	private static Logger logger = LoggerFactory.getLogger("DataLoader.class");
	private static DataLoader instance; 
	private static String dataFile = "products.json";
	public static List<String> stopWords = new ArrayList<String>();
	private DataLoader(){
//		loadStopWords();
	}
    
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
	public static void load(IAutoComplete storeImpl){
		try {
			logger.info("{}, loading data from {}", storeImpl.getImplName(), DataLoader.dataFile);
			long startTime = System.currentTimeMillis();
			int totalItems = 0;
//			ACTrieImpl storeImpl = new ACTrieImpl();
			ClassLoader classLoader = DataLoader.class.getClassLoader();
			JSONParser parser = new JSONParser();
			JSONArray prods = (JSONArray) parser.parse(new FileReader(classLoader.getResource(DataLoader.dataFile).getFile()));
			Iterator<JSONObject> iterator = prods.iterator();
			while (iterator.hasNext()) {
				JSONObject prod = (JSONObject) iterator.next();
				String prodName = (String) prod.get("name");
				storeImpl.addProdName(prodName, totalItems);
//				System.out.println("name: " + prodName);
				totalItems ++;
				
			}
//			System.out.println("count:" + totalItems);
			long totalTime = System.currentTimeMillis() - startTime;
			logger.info("{}, load completed, total {} items, spent {} ms", storeImpl.getImplName(), totalItems, totalTime);
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void loadStopWords(){
		BufferedReader br = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			br = new BufferedReader(new FileReader(classLoader.getResource("stopwords.txt").getFile()));
		    String line = br.readLine();
		    while (line != null) {
		    	stopWords.add(line);
		        line = br.readLine();
		    }
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (null != br){
				 try{
					 br.close();
			     }catch (Exception e){
			    	 logger.error(e.getMessage());
			     }
			  }
		}
	}

}
