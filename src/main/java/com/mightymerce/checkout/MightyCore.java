package com.mightymerce.checkout;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

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

    public void createOrder(Article article) {
        Order order = new Order();
        order.setTest(article.getArticleId());
        getRestTemplate().postForObject(coreUrl+"/api/socialOrders/",order,Order.class);
    }
}
