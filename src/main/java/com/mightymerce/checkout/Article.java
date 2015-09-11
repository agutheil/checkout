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

    private String currency;

    public String getArticleId() {
        return articleId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

<<<<<<< HEAD
    public String getPaypal() {
        return paypal;
    }
    
    public void setArticleId(String articleId) {
=======
	public void setArticleId(String articleId) {
>>>>>>> adding pp
		this.articleId = articleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

<<<<<<< HEAD
	public void setPaypal(String paypal) {
		this.paypal = paypal;
	}
=======
>>>>>>> adding pp
}
