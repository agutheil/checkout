package com.mightymerce.checkout.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mightymerce.checkout.domain.Order;
import com.mightymerce.checkout.repository.OrderRepository;
import com.mightymerce.checkout.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);

    @Inject
    private OrderRepository orderRepository;

    /**
     * POST  /orders -> Create a new order.
     */
    @RequestMapping(value = "/orders",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> create(@RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to save Order : {}", order);
        if (order.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new order cannot already have an ID").body(null);
        }
        Order result = orderRepository.save(order);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("order", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /orders -> Updates an existing order.
     */
    @RequestMapping(value = "/orders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> update(@RequestBody Order order) throws URISyntaxException {
        log.debug("REST request to update Order : {}", order);
        if (order.getId() == null) {
            return create(order);
        }
        Order result = orderRepository.save(order);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("order", order.getId().toString()))
                .body(result);
    }

    /**
     * GET  /orders -> get all the orders.
     */
    @RequestMapping(value = "/orders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Order> getAll() {
        log.debug("REST request to get all Orders");
        return orderRepository.findAll();
    }

    /**
     * GET  /orders/:id -> get the "id" order.
     */
    @RequestMapping(value = "/orders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Order> get(@PathVariable String id) {
        log.debug("REST request to get Order : {}", id);
        return Optional.ofNullable(orderRepository.findOne(id))
            .map(order -> new ResponseEntity<>(
                order,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orders/:id -> delete the "id" order.
     */
    @RequestMapping(value = "/orders/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("REST request to delete Order : {}", id);
        orderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("order", id.toString())).build();
    }
}
