package com.mightymerce.checkout;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {
    	
    	orderRepository.deleteAll();
    	
//    	Order order = new Order();
//    	order.setArticle(1L);
//    	orderRepository.save(order);
//    	
//    	Order order2 = new Order();
//    	order2.setArticle(2L);
//    	orderRepository.save(order2);
    	
    	// fetch all customers
		System.out.println("Orders found with findAll():");
		System.out.println("-------------------------------");
		for (Order o : orderRepository.findAll()) {
			System.out.println(o);
		}
		System.out.println();

		articleRepository.deleteAll();

		// save a couple of customers
		articleRepository.save(new Article("1", "Artikel 1", "Beschreibung 1", BigDecimal.valueOf(11.55), BigDecimal.valueOf(3.95), "EUR"));
		articleRepository.save(new Article("2", "Artikel 2", "Beschreibung 2", BigDecimal.valueOf(199.55), BigDecimal.valueOf(13.95), "EUR"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Article customer : articleRepository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Article found with findByArticleId('1'):");
		System.out.println("--------------------------------");
		System.out.println(articleRepository.findByArticleId("1"));
		System.out.println();

		System.out.println("Article found with findByArticleId('2'):");
		System.out.println("--------------------------------");
		System.out.println(articleRepository.findByArticleId("1"));

	}

} 