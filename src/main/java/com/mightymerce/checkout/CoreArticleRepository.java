package com.mightymerce.checkout;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class CoreArticleRepository {
	private OAuth2Template oAuth2Template;

	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

    @Value("${mightymerce.coreUrl}")
    private String coreUrl;
    
    @Value("${mightymerce.istestmode}")
    private boolean isTestmode;

    @Inject
    public CoreArticleRepository(OAuth2Template oAuth2Template) {
        this.oAuth2Template = oAuth2Template;
        params.set("scope", "read write");

    }
    
    public Article retrieveArticle(String articleId) {
		Article article;
		if (!isTestmode){
			AccessGrant ag = oAuth2Template.exchangeCredentialsForAccess("admin", "admin",params);
	        MightyCore mightyCore = new MightyCore(ag.getAccessToken(), TokenStrategy.AUTHORIZATION_HEADER, coreUrl);
	        article = mightyCore.getArticle(articleId);
		} else {
			article = new Article();
			article.setName("Mein Name");
			article.setPrice(BigDecimal.valueOf(11.55));
			article.setDeliveryCosts(BigDecimal.valueOf(3.95));
			article.setCurrency("EUR");
			article.setDescription("Meine Beschreibung");
		}
		return article;
	}
}
