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

import com.mightymerce.checkout.domain.Article;

@Repository
public class CoreArticleRepository {
	private OAuth2Template oAuth2Template;

	private MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

    @Value("${mightymerce.coreUrl}")
    private String coreUrl;
    
    @Value("${mightymerce.coreUser}")
    private String coreUser;
    
    @Value("${mightymerce.corePassword}")
    private String corePassword;

    @Inject
    public CoreArticleRepository(OAuth2Template oAuth2Template) {
        this.oAuth2Template = oAuth2Template;
        params.set("scope", "read");
    }
    
    public List<CoreArticle> retrieveArticles() {
		AccessGrant ag = oAuth2Template.exchangeCredentialsForAccess(coreUser, corePassword, params);
        MightyCore mightyCore = new MightyCore(ag.getAccessToken(), TokenStrategy.AUTHORIZATION_HEADER, coreUrl);
        List<CoreArticle> coreArticles = mightyCore.getArticles();
		return coreArticles;
	}
}
