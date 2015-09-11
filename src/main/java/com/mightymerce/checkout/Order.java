package com.mightymerce.checkout;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    
    private Long article;
    
    private String payerId;
    
    private String transactionId;
    
    private String paymentStatus;
    
    private BigDecimal amount;

	public void setArticle(Long article) {
		this.article = article;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setTransactionId(String txId) {
		this.transactionId = txId;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getArticle() {
		return article;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
