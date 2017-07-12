package com.wesley.autocomplete;

import java.util.List;

public interface IAutoComplete {
	public void addProdName(String prodName, int order);
	public List<String> getSuggestions(String prefix);
	public String getImplName();
}
