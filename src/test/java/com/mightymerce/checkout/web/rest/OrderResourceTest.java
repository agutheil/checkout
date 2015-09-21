package com.mightymerce.checkout.web.rest;

import com.mightymerce.checkout.Application;
import com.mightymerce.checkout.domain.Order;
import com.mightymerce.checkout.repository.OrderRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mightymerce.checkout.domain.enumeration.Currency;

/**
 * Test class for the OrderResource REST controller.
 *
 * @see OrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderResourceTest {


    private static final Long DEFAULT_CORE_ARTICLE_ID = 1L;
    private static final Long UPDATED_CORE_ARTICLE_ID = 2L;
    private static final String DEFAULT_TRANSATION_ID = "SAMPLE_TEXT";
    private static final String UPDATED_TRANSATION_ID = "UPDATED_TEXT";
    private static final String DEFAULT_PAYMENT_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_PAYMENT_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_PAYER_ID = "SAMPLE_TEXT";
    private static final String UPDATED_PAYER_ID = "UPDATED_TEXT";
    private static final String DEFAULT_PAYER_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_PAYER_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_FIRST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FIRST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_LAST_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_LAST_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_STREET = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_STREET = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_CITY = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_CITY = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_STATE = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_CNTRY_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_CNTRY_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_SHIP_TO_ZIP = "SAMPLE_TEXT";
    private static final String UPDATED_SHIP_TO_ZIP = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS_STATUS = "UPDATED_TEXT";
    private static final String DEFAULT_TOTAL_AMT = "SAMPLE_TEXT";
    private static final String UPDATED_TOTAL_AMT = "UPDATED_TEXT";

    private static final Currency DEFAULT_CURRENCY_CODE = Currency.EUR;
    private static final Currency UPDATED_CURRENCY_CODE = Currency.GBP;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOrderMockMvc;

    private Order order;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderResource orderResource = new OrderResource();
        ReflectionTestUtils.setField(orderResource, "orderRepository", orderRepository);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        orderRepository.deleteAll();
        order = new Order();
        order.setCoreArticleId(DEFAULT_CORE_ARTICLE_ID);
        order.setTransationId(DEFAULT_TRANSATION_ID);
        order.setPaymentStatus(DEFAULT_PAYMENT_STATUS);
        order.setEmail(DEFAULT_EMAIL);
        order.setPayerId(DEFAULT_PAYER_ID);
        order.setPayerStatus(DEFAULT_PAYER_STATUS);
        order.setFirstName(DEFAULT_FIRST_NAME);
        order.setLastName(DEFAULT_LAST_NAME);
        order.setShipToName(DEFAULT_SHIP_TO_NAME);
        order.setShipToStreet(DEFAULT_SHIP_TO_STREET);
        order.setShipToCity(DEFAULT_SHIP_TO_CITY);
        order.setShipToState(DEFAULT_SHIP_TO_STATE);
        order.setShipToCntryCode(DEFAULT_SHIP_TO_CNTRY_CODE);
        order.setShipToZip(DEFAULT_SHIP_TO_ZIP);
        order.setAddressStatus(DEFAULT_ADDRESS_STATUS);
        order.setTotalAmt(DEFAULT_TOTAL_AMT);
        order.setCurrencyCode(DEFAULT_CURRENCY_CODE);
    }

    @Test
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order

        restOrderMockMvc.perform(post("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orders.get(orders.size() - 1);
        assertThat(testOrder.getCoreArticleId()).isEqualTo(DEFAULT_CORE_ARTICLE_ID);
        assertThat(testOrder.getTransationId()).isEqualTo(DEFAULT_TRANSATION_ID);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrder.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrder.getPayerId()).isEqualTo(DEFAULT_PAYER_ID);
        assertThat(testOrder.getPayerStatus()).isEqualTo(DEFAULT_PAYER_STATUS);
        assertThat(testOrder.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOrder.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testOrder.getShipToName()).isEqualTo(DEFAULT_SHIP_TO_NAME);
        assertThat(testOrder.getShipToStreet()).isEqualTo(DEFAULT_SHIP_TO_STREET);
        assertThat(testOrder.getShipToCity()).isEqualTo(DEFAULT_SHIP_TO_CITY);
        assertThat(testOrder.getShipToState()).isEqualTo(DEFAULT_SHIP_TO_STATE);
        assertThat(testOrder.getShipToCntryCode()).isEqualTo(DEFAULT_SHIP_TO_CNTRY_CODE);
        assertThat(testOrder.getShipToZip()).isEqualTo(DEFAULT_SHIP_TO_ZIP);
        assertThat(testOrder.getAddressStatus()).isEqualTo(DEFAULT_ADDRESS_STATUS);
        assertThat(testOrder.getTotalAmt()).isEqualTo(DEFAULT_TOTAL_AMT);
        assertThat(testOrder.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
    }

    @Test
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get all the orders
        restOrderMockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId())))
                .andExpect(jsonPath("$.[*].coreArticleId").value(hasItem(DEFAULT_CORE_ARTICLE_ID.intValue())))
                .andExpect(jsonPath("$.[*].transationId").value(hasItem(DEFAULT_TRANSATION_ID.toString())))
                .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].payerId").value(hasItem(DEFAULT_PAYER_ID.toString())))
                .andExpect(jsonPath("$.[*].payerStatus").value(hasItem(DEFAULT_PAYER_STATUS.toString())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].shipToName").value(hasItem(DEFAULT_SHIP_TO_NAME.toString())))
                .andExpect(jsonPath("$.[*].shipToStreet").value(hasItem(DEFAULT_SHIP_TO_STREET.toString())))
                .andExpect(jsonPath("$.[*].shipToCity").value(hasItem(DEFAULT_SHIP_TO_CITY.toString())))
                .andExpect(jsonPath("$.[*].shipToState").value(hasItem(DEFAULT_SHIP_TO_STATE.toString())))
                .andExpect(jsonPath("$.[*].shipToCntryCode").value(hasItem(DEFAULT_SHIP_TO_CNTRY_CODE.toString())))
                .andExpect(jsonPath("$.[*].shipToZip").value(hasItem(DEFAULT_SHIP_TO_ZIP.toString())))
                .andExpect(jsonPath("$.[*].addressStatus").value(hasItem(DEFAULT_ADDRESS_STATUS.toString())))
                .andExpect(jsonPath("$.[*].totalAmt").value(hasItem(DEFAULT_TOTAL_AMT.toString())))
                .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())));
    }

    @Test
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(order.getId()))
            .andExpect(jsonPath("$.coreArticleId").value(DEFAULT_CORE_ARTICLE_ID.intValue()))
            .andExpect(jsonPath("$.transationId").value(DEFAULT_TRANSATION_ID.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.payerId").value(DEFAULT_PAYER_ID.toString()))
            .andExpect(jsonPath("$.payerStatus").value(DEFAULT_PAYER_STATUS.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.shipToName").value(DEFAULT_SHIP_TO_NAME.toString()))
            .andExpect(jsonPath("$.shipToStreet").value(DEFAULT_SHIP_TO_STREET.toString()))
            .andExpect(jsonPath("$.shipToCity").value(DEFAULT_SHIP_TO_CITY.toString()))
            .andExpect(jsonPath("$.shipToState").value(DEFAULT_SHIP_TO_STATE.toString()))
            .andExpect(jsonPath("$.shipToCntryCode").value(DEFAULT_SHIP_TO_CNTRY_CODE.toString()))
            .andExpect(jsonPath("$.shipToZip").value(DEFAULT_SHIP_TO_ZIP.toString()))
            .andExpect(jsonPath("$.addressStatus").value(DEFAULT_ADDRESS_STATUS.toString()))
            .andExpect(jsonPath("$.totalAmt").value(DEFAULT_TOTAL_AMT.toString()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()));
    }

    @Test
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

		int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        order.setCoreArticleId(UPDATED_CORE_ARTICLE_ID);
        order.setTransationId(UPDATED_TRANSATION_ID);
        order.setPaymentStatus(UPDATED_PAYMENT_STATUS);
        order.setEmail(UPDATED_EMAIL);
        order.setPayerId(UPDATED_PAYER_ID);
        order.setPayerStatus(UPDATED_PAYER_STATUS);
        order.setFirstName(UPDATED_FIRST_NAME);
        order.setLastName(UPDATED_LAST_NAME);
        order.setShipToName(UPDATED_SHIP_TO_NAME);
        order.setShipToStreet(UPDATED_SHIP_TO_STREET);
        order.setShipToCity(UPDATED_SHIP_TO_CITY);
        order.setShipToState(UPDATED_SHIP_TO_STATE);
        order.setShipToCntryCode(UPDATED_SHIP_TO_CNTRY_CODE);
        order.setShipToZip(UPDATED_SHIP_TO_ZIP);
        order.setAddressStatus(UPDATED_ADDRESS_STATUS);
        order.setTotalAmt(UPDATED_TOTAL_AMT);
        order.setCurrencyCode(UPDATED_CURRENCY_CODE);
        

        restOrderMockMvc.perform(put("/api/orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(order)))
                .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orders.get(orders.size() - 1);
        assertThat(testOrder.getCoreArticleId()).isEqualTo(UPDATED_CORE_ARTICLE_ID);
        assertThat(testOrder.getTransationId()).isEqualTo(UPDATED_TRANSATION_ID);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrder.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrder.getPayerId()).isEqualTo(UPDATED_PAYER_ID);
        assertThat(testOrder.getPayerStatus()).isEqualTo(UPDATED_PAYER_STATUS);
        assertThat(testOrder.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOrder.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOrder.getShipToName()).isEqualTo(UPDATED_SHIP_TO_NAME);
        assertThat(testOrder.getShipToStreet()).isEqualTo(UPDATED_SHIP_TO_STREET);
        assertThat(testOrder.getShipToCity()).isEqualTo(UPDATED_SHIP_TO_CITY);
        assertThat(testOrder.getShipToState()).isEqualTo(UPDATED_SHIP_TO_STATE);
        assertThat(testOrder.getShipToCntryCode()).isEqualTo(UPDATED_SHIP_TO_CNTRY_CODE);
        assertThat(testOrder.getShipToZip()).isEqualTo(UPDATED_SHIP_TO_ZIP);
        assertThat(testOrder.getAddressStatus()).isEqualTo(UPDATED_ADDRESS_STATUS);
        assertThat(testOrder.getTotalAmt()).isEqualTo(UPDATED_TOTAL_AMT);
        assertThat(testOrder.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

		int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Get the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
