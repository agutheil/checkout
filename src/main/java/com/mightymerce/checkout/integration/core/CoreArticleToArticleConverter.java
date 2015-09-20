package com.mightymerce.checkout.integration.core;

import org.springframework.stereotype.Component;

import com.mightymerce.checkout.Article;

@Component
public class CoreArticleToArticleConverter {
	Article convert(CoreArticle coreArticle) {
		Article article = new Article();
		article.setArticleId(coreArticle.getId());
		article.setCode(coreArticle.getCode());
		article.setName(coreArticle.getName());
		article.setDescription(coreArticle.getDescription());
		article.setPrice(coreArticle.getPrice());
		article.setDeliveryCosts(coreArticle.getDeliveryCosts());
		article.setCurrency(coreArticle.getCurrency());
		article.setImage1(coreArticle.getImage1());
		article.setImage2(coreArticle.getImage2());
		return article;
	}
}
