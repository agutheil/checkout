package com.mightymerce.checkout.web.rest;

import com.mightymerce.checkout.Application;
import com.mightymerce.checkout.domain.Article;
import com.mightymerce.checkout.repository.ArticleRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mightymerce.checkout.domain.enumeration.Currency;

/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ArticleResourceTest {


    private static final Long DEFAULT_CORE_ID = 1L;
    private static final Long UPDATED_CORE_ID = 2L;
    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DELIVERY_COSTS = new BigDecimal(1);
    private static final BigDecimal UPDATED_DELIVERY_COSTS = new BigDecimal(2);

    private static final Currency DEFAULT_CURRENCY = Currency.EUR;
    private static final Currency UPDATED_CURRENCY = Currency.GBP;

    private static final byte[] DEFAULT_IMAGE1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE1 = TestUtil.createByteArray(2, "1");

    private static final byte[] DEFAULT_IMAGE2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE2 = TestUtil.createByteArray(2, "1");

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restArticleMockMvc;

    private Article article;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArticleResource articleResource = new ArticleResource();
        ReflectionTestUtils.setField(articleResource, "articleRepository", articleRepository);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        articleRepository.deleteAll();
        article = new Article();
        article.setCoreId(DEFAULT_CORE_ID);
        article.setCode(DEFAULT_CODE);
        article.setName(DEFAULT_NAME);
        article.setDescription(DEFAULT_DESCRIPTION);
        article.setPrice(DEFAULT_PRICE);
        article.setDeliveryCosts(DEFAULT_DELIVERY_COSTS);
        article.setCurrency(DEFAULT_CURRENCY);
        article.setImage1(DEFAULT_IMAGE1);
        article.setImage2(DEFAULT_IMAGE2);
    }

    @Test
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article

        restArticleMockMvc.perform(post("/api/articles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articles.get(articles.size() - 1);
        assertThat(testArticle.getCoreId()).isEqualTo(DEFAULT_CORE_ID);
        assertThat(testArticle.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testArticle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArticle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArticle.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testArticle.getDeliveryCosts()).isEqualTo(DEFAULT_DELIVERY_COSTS);
        assertThat(testArticle.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testArticle.getImage1()).isEqualTo(DEFAULT_IMAGE1);
        assertThat(testArticle.getImage2()).isEqualTo(DEFAULT_IMAGE2);
    }

    @Test
    public void checkCoreIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setCoreId(null);

        // Create the Article, which fails.

        restArticleMockMvc.perform(post("/api/articles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isBadRequest());

        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = articleRepository.findAll().size();
        // set the field null
        article.setCode(null);

        // Create the Article, which fails.

        restArticleMockMvc.perform(post("/api/articles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isBadRequest());

        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.save(article);

        // Get all the articles
        restArticleMockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId())))
                .andExpect(jsonPath("$.[*].coreId").value(hasItem(DEFAULT_CORE_ID.intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].deliveryCosts").value(hasItem(DEFAULT_DELIVERY_COSTS.intValue())))
                .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
                .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE1))))
                .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE2))));
    }

    @Test
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.save(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(article.getId()))
            .andExpect(jsonPath("$.coreId").value(DEFAULT_CORE_ID.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.deliveryCosts").value(DEFAULT_DELIVERY_COSTS.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.image1").value(Base64Utils.encodeToString(DEFAULT_IMAGE1)))
            .andExpect(jsonPath("$.image2").value(Base64Utils.encodeToString(DEFAULT_IMAGE2)));
    }

    @Test
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateArticle() throws Exception {
        // Initialize the database
        articleRepository.save(article);

		int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        article.setCoreId(UPDATED_CORE_ID);
        article.setCode(UPDATED_CODE);
        article.setName(UPDATED_NAME);
        article.setDescription(UPDATED_DESCRIPTION);
        article.setPrice(UPDATED_PRICE);
        article.setDeliveryCosts(UPDATED_DELIVERY_COSTS);
        article.setCurrency(UPDATED_CURRENCY);
        article.setImage1(UPDATED_IMAGE1);
        article.setImage2(UPDATED_IMAGE2);
        

        restArticleMockMvc.perform(put("/api/articles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(article)))
                .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articles.get(articles.size() - 1);
        assertThat(testArticle.getCoreId()).isEqualTo(UPDATED_CORE_ID);
        assertThat(testArticle.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testArticle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArticle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticle.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testArticle.getDeliveryCosts()).isEqualTo(UPDATED_DELIVERY_COSTS);
        assertThat(testArticle.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testArticle.getImage1()).isEqualTo(UPDATED_IMAGE1);
        assertThat(testArticle.getImage2()).isEqualTo(UPDATED_IMAGE2);
    }

    @Test
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleRepository.save(article);

		int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Get the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articles = articleRepository.findAll();
        assertThat(articles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
