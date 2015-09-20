package com.mightymerce.checkout.integration.core;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.mightymerce.checkout.Article;

@Repository
public class CoreArticleRepository {
	private OAuth2Template oAuth2Template;
	
	private CoreArticleToArticleConverter converter;

	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

    @Value("${mightymerce.coreUrl}")
    private String coreUrl;
    
    @Value("${mightymerce.istestmode}")
    private boolean isTestmode;

    @Inject
    public CoreArticleRepository(OAuth2Template oAuth2Template, CoreArticleToArticleConverter converter) {
        this.oAuth2Template = oAuth2Template;
        params.set("scope", "read write");
        this.converter = converter;

    }
    
    public Article retrieveArticle(String articleId) {
    	Article article;
    	AccessGrant ag = oAuth2Template.exchangeCredentialsForAccess("admin", "admin",params);
    	MightyCore mightyCore = new MightyCore(ag.getAccessToken(), TokenStrategy.AUTHORIZATION_HEADER, coreUrl);
    	CoreArticle coreArticle = mightyCore.getArticle(articleId);
    	article = converter.convert(coreArticle);
    	return article;
    }
    public List<Article> retrieveArticles() {
		List<Article> articles = new ArrayList<>();
		AccessGrant ag = oAuth2Template.exchangeCredentialsForAccess("admin", "admin",params);
        MightyCore mightyCore = new MightyCore(ag.getAccessToken(), TokenStrategy.AUTHORIZATION_HEADER, coreUrl);
        List<CoreArticle> coreArticles = mightyCore.getArticles();
        for (CoreArticle coreArticle : coreArticles) {
        	articles.add(converter.convert(coreArticle));
		}
		return articles;
	}
}
