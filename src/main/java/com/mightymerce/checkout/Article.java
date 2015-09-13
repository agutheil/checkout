package com.mightymerce.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/**
 * Created by agutheil on 11.05.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {

	private String articleId;

    private String name;

    private String description;

    private BigDecimal price;
    
    private BigDecimal deliveryCosts;

    private String currency;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDeliveryCosts() {
		return deliveryCosts;
	}

	public void setDeliveryCosts(BigDecimal deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

    
}
