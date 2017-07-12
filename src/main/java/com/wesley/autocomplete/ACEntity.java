package com.wesley.autocomplete;

public class ACEntity {
    private String keyword;
    public int order;

    public ACEntity() {}
    
    public ACEntity(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public static ACEntity fromFormattedString(String formmatedLine) {
    	ACEntity res = null;
        if (!Utils.isValid(formmatedLine)) return null;
        String[] temp = formmatedLine.split("\t");
        String word = temp[0];
        if (Utils.isValid(word)) {
            res = new ACEntity(word);
            return res;
        } else {
            return null;
        }
    }
}
