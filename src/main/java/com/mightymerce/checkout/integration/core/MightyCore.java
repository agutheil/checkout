package com.mightymerce.checkout.integration.core;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.mightymerce.checkout.domain.Article;
import com.mightymerce.checkout.domain.Order;

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

    public CoreArticle getArticle(String articleId) {
    	CoreArticle article = getRestTemplate().getForObject(coreUrl+"/api/articles/"+articleId, CoreArticle.class);
        return article;
    }
    public List<CoreArticle> getArticles() {
    	ResponseEntity<CoreArticle[]> responseEntity = getRestTemplate().getForEntity(coreUrl+"/api/articles", CoreArticle[].class);
    	CoreArticle[] articles = responseEntity.getBody();
    	return Arrays.asList(articles);
    }
    
    public void createOrder(Order order) {
        getRestTemplate().postForObject(coreUrl+"/api/flatSocialOrders/",order,Order.class);
    }
}
