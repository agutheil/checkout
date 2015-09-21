package com.mightymerce.checkout.repository;

import com.mightymerce.checkout.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Order entity.
 */
public interface OrderRepository extends MongoRepository<Order,String> {

}
