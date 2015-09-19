package com.mightymerce.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

/**
 * Created by agutheil on 11.05.15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
	
	public Article() {
		super();
	}

	public Article(String articleId, String name, String description, BigDecimal price, BigDecimal deliveryCosts,
			String currency) {
		super();
		this.articleId = articleId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.deliveryCosts = deliveryCosts;
		this.currency = currency;
	}

	@Id private String id;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleId == null) ? 0 : articleId.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((deliveryCosts == null) ? 0 : deliveryCosts.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (deliveryCosts == null) {
			if (other.deliveryCosts != null)
				return false;
		} else if (!deliveryCosts.equals(other.deliveryCosts))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", articleId=" + articleId + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", deliveryCosts=" + deliveryCosts + ", currency=" + currency + "]";
	}

    
}
