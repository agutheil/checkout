package com.mightymerce.checkout;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
        return "checkout";
    }

	private Article retrieveArticle(Long articleId) {
		return articleRepository.findByArticleId(articleId);
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
