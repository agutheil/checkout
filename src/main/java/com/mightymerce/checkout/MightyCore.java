package com.mightymerce.checkout;

import java.math.BigDecimal;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.mightymerce.checkout.Order;

/**
 * Created by agutheil on 11.05.15.
 */
public class MightyCore extends AbstractOAuth2ApiBinding {
    private String coreUrl;

    protected MightyCore() {
        super();
    }

    protected MightyCore(String accessToken) {
        super(accessToken);
    }

    protected MightyCore(String accessToken, TokenStrategy tokenStrategy) {
        super(accessToken, tokenStrategy);
    }

    protected MightyCore(String accessToken, TokenStrategy tokenStrategy, String coreUrl) {
        super(accessToken, tokenStrategy);
        this.coreUrl = coreUrl;
    }

    public Article getArticle(String articleId) {
        Article article = getRestTemplate().getForObject(coreUrl+"/api/articles/"+articleId, Article.class);
        return article;
    }
    
    public void createOrder(Long articleId, String payerId, String txId, String paymentStatus, BigDecimal amount) {
        Order order = new Order();
        order.setArticle(articleId);
        order.setPayerId(payerId);
        order.setTransactionId(txId);
        order.setPaymentStatus(paymentStatus.toLowerCase());
        order.setAmount(amount);
        getRestTemplate().postForObject(coreUrl+"/api/flatSocialOrders/",order,Order.class);
    }
}
