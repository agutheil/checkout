package com.mightymerce.checkout.web.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mightymerce.checkout.domain.Article;
import com.mightymerce.checkout.domain.enumeration.Currency;
import com.mightymerce.checkout.repository.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/")
public class CheckoutController {
	
	@Autowired
	private ArticleRepository articleRepository;

    @RequestMapping(value = "checkout/{articleId}", method=RequestMethod.GET)
    public String checkout(@PathVariable Long articleId, Model model) {
        model.addAttribute("articleId", articleId);
        Article article = retrieveArticle(articleId);
        model.addAttribute("articleName",article.getName());
        model.addAttribute("price",article.getPrice());
        model.addAttribute("deliveryCosts",article.getDeliveryCosts());
        model.addAttribute("currency",article.getCurrency());
        model.addAttribute("description",article.getDescription());
        model.addAttribute("shippingAmt",article.getDeliveryCosts());
        model.addAttribute("logoUrl","/checkout/"+articleId+"/image1");
        model.addAttribute("imageUrl","/checkout/"+articleId+"/image2");
        return "checkout";
    }
    
    @RequestMapping(value = "checkout/{articleId}/image1", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> serveImage1(@PathVariable Long articleId) throws IOException {
    	Article article = retrieveArticle(articleId);
    	byte[] bytes = article.getImage1();
    	InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
    	String mimeType = URLConnection.guessContentTypeFromStream(is);
    	return ResponseEntity.ok()
    			.contentLength(bytes.length)
    			.contentType(MediaType.parseMediaType(mimeType))
    			.body(new InputStreamResource(is));
    }
    
    @RequestMapping(value = "checkout/{articleId}/image2", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> serveImage2(@PathVariable Long articleId) throws IOException {
    	Article article = retrieveArticle(articleId);
    	byte[] bytes = article.getImage2();
    	InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
    	String mimeType = URLConnection.guessContentTypeFromStream(is);
    	return ResponseEntity.ok()
    			.contentLength(bytes.length)
    			.contentType(MediaType.parseMediaType(mimeType))
    			.body(new InputStreamResource(is));
    }

	private Article retrieveArticle(Long articleId) {
		return articleRepository.findByCoreId(articleId);
	}

    @RequestMapping(value = "success", method=RequestMethod.GET)
    public String success() {
        return "success";
    }

    @RequestMapping(value = "cancel", method=RequestMethod.GET)
    public String cancel() {
        return "cancel";
    }



}
