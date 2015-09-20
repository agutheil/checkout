package com.mightymerce.checkout.integration.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mightymerce.checkout.Article;
import com.mightymerce.checkout.ArticleRepository;

@Component
public class CoreArticleImporter {
	private final Logger log = LoggerFactory.getLogger(CoreArticleImporter.class);
	@Autowired
	CoreArticleRepository coreArticleRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Scheduled(fixedRate = 5000)
	public void importArticles() {
		log.info("Importing Articles from core ...");
		List<Article> articles = coreArticleRepository.retrieveArticles();
		for (Article article : articles) {
			log.info("Checking if article exists with articleId "+article.getArticleId());
			if (articleRepository.findByArticleId(article.getArticleId()) == null) {
				log.info("Saving article ..."+article);
				articleRepository.save(article);
			} else {
				log.info("Article already exists");
			}
		}
	}
}
