package com.wesley.autocomplete;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

public class ACTrieImpl implements IAutoComplete{
    private static PatriciaTrie<ACEntity> trie = new PatriciaTrie<ACEntity>();
    private static final Object lock = new Object();
    
    public void addProdName(String prodName, int order) {
        synchronized (lock) {
            ACEntity prod = ACEntity.fromFormattedString(prodName);
            if (prod != null) {
            	prod.order = order; 
                trie.put(prod.getKeyword().toLowerCase(), prod);
            }

       }
    }
    
    public List<String> getSuggestions(String prefix){
    	List<String> ret = new ArrayList<String>();
    	if (trie == null || trie.isEmpty() || prefix.trim() == "") {
           return ret;
        }
        
        if (Utils.isValid(prefix) ) {
            SortedMap<String, ACEntity> map = trie.prefixMap(prefix);
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

	@Override
	public String getImplName() {
		// TODO Auto-generated method stub
		return "trie";
	}
}
