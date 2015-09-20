package com.mightymerce.checkout;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "article", path = "article")
public interface ArticleRepository extends MongoRepository<Article, String>{

	Article findByArticleId(@Param("articleId") Long articleId);

}
