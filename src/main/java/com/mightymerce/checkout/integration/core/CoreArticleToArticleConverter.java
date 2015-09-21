package com.mightymerce.checkout.integration.core;

import org.springframework.stereotype.Component;

import com.mightymerce.checkout.domain.Article;
import com.mightymerce.checkout.domain.enumeration.Currency;

@Component
public class CoreArticleToArticleConverter {
	Article convert(CoreArticle coreArticle) {
		Article article = new Article();
		article.setCoreId(coreArticle.getId());
		article.setCode(coreArticle.getCode());
		article.setName(coreArticle.getName());
		article.setDescription(coreArticle.getDescription());
		article.setPrice(coreArticle.getPrice());
		article.setDeliveryCosts(coreArticle.getDeliveryCosts());
		article.setCurrency(Currency.valueOf(coreArticle.getCurrency()));
		article.setImage1(coreArticle.getImage1());
		article.setImage2(coreArticle.getImage2());
		return article;
	}
}
