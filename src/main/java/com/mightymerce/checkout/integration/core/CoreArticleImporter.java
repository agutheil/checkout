package com.mightymerce.checkout.integration.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mightymerce.checkout.domain.Article;
import com.mightymerce.checkout.repository.ArticleRepository;

@Component
public class CoreArticleImporter {
	private final Logger log = LoggerFactory.getLogger(CoreArticleImporter.class);
	
	@Autowired
	CoreArticleToArticleConverter converter;
	
	@Autowired
	CoreArticleRepository coreArticleRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Scheduled(fixedRate = 5000)
	public void importArticles() {
		log.info("Importing Articles from core ...");
		List<CoreArticle> coreArticles = coreArticleRepository.retrieveArticles();
		for (CoreArticle coreArticle : coreArticles) {
			log.info("Importing article with articleId "+coreArticle.getId());
			Article article = articleRepository.findByCoreId(coreArticle.getId());
			if ( article == null) {
				log.info("Creating article ...");
				article = converter.convert(new Article(), coreArticle);
			} else {
				log.info("Updating article ...");
				article = converter.convert(article, coreArticle);
			}
			articleRepository.save(article);
		}
	}
}
