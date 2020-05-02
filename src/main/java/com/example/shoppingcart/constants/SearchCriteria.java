/**
 * 
 */
package com.example.shoppingcart.constants;

/**
 * @author Shakti
 *
 */
public enum SearchCriteria {

	ID("id"), NAME("name"), CATEGORY("cat");

	private String searchCriteriaInfo;

	private SearchCriteria(String string) {
		this.searchCriteriaInfo = string;
	}

	public String getSerachCriteria() {
		return this.searchCriteriaInfo;
	}
}
