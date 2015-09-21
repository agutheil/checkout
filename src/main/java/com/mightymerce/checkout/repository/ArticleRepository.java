package com.mightymerce.checkout.repository;

import com.mightymerce.checkout.domain.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data MongoDB repository for the Article entity.
 */
public interface ArticleRepository extends MongoRepository<Article,String> {
	Article findByCoreId(@Param("coreId") Long coreId);
}
