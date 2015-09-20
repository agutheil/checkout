package com.mightymerce.checkout;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends MongoRepository<Article, String>{

	Article findByArticleId(@Param("articleId") Long articleId);

}
