package com.mightymerce.checkout.integration.core;

import java.math.BigDecimal;

public class CoreArticle {
	
    private Long id;
    
    private String code;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal deliveryCosts;

    private String currency;

    private byte[] image1;

    private byte[] image2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public byte[] getImage1() {
		return image1;
	}

	public void setImage1(byte[] image1) {
		this.image1 = image1;
	}

	public byte[] getImage2() {
		return image2;
	}

	public void setImage2(byte[] image2) {
		this.image2 = image2;
	}

}
