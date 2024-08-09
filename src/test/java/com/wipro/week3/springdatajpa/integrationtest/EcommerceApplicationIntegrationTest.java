package com.wipro.week3.springdatajpa.integrationtest;

import com.wipro.week3.springdatajpa.SpringDataJpaApplication;
import com.wipro.week3.springdatajpa.controller.OrderController;
import com.wipro.week3.springdatajpa.controller.ProductController;
import com.wipro.week3.springdatajpa.dtos.OrderForm;
import com.wipro.week3.springdatajpa.dtos.OrderProductDto;
import com.wipro.week3.springdatajpa.entities.Order;
import com.wipro.week3.springdatajpa.entities.Product;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = {SpringDataJpaApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EcommerceApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ProductController productController;

    @Autowired
    private OrderController orderController;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(productController);
        Assertions.assertNotNull(orderController);
    }

    //@Test
    void givenGetProductsApiCall_whenProductListRetrieved_thenSizeMatchAndListContainsProductNames() {
        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api" + "/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        });
        List<Product> products = responseEntity.getBody();
        Assertions.assertNotNull(products);
        //Assertions.assertEquals(7, products.size());

        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("TV Set"))));
        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("Game Console"))));
        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("Sofa"))));
        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("Icecream"))));
        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("Beer"))));
        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("Phone"))));
        MatcherAssert.assertThat(products, hasItem(hasProperty("name", is("Watch"))));
    }

    @Test
    void givenGetOrdersApiCall_whenProductListRetrieved_thenSizeMatchAndListContainsProductNames() {
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/orders", HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
        });

        List<Order> orders = responseEntity.getBody();
        Assertions.assertNotNull(orders);
        Assertions.assertEquals(0, orders.size());
    }

    //@Test
    void givenPostOrder_whenBodyRequestMatcherJson_thenResponseContainsEqualObjectProperties() {
        final ResponseEntity<Order> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/api/orders", prepareOrderForm(), Order.class);
        Order order = postResponse.getBody();
        //Assertions.assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());

        MatcherAssert.assertThat(order, hasProperty("status", is("PAID")));
        MatcherAssert.assertThat(order.getOrderProducts(), hasItem(hasProperty("quantity", is(2))));
    }

    private OrderForm prepareOrderForm() {
        OrderForm orderForm = new OrderForm();
        OrderProductDto productDto = new OrderProductDto();
        productDto.setProduct(new Product(1L, "TV Set", "Electronics", 300.00, "http://placehold.it/200x100"));
        productDto.setQuantity(2);
        orderForm.setProductOrders(Collections.singletonList(productDto));

        return orderForm;
    }
}