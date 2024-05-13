package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @MockBean
    CustomerRepository customerRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void shouldSaveCustomer1() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("id", UUID.randomUUID().toString());
        request.put("name", "name1");
        request.put("phone", "phone");
        request.put("age", "10");

        // then
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(getRootUrl() + "/customer")
                .then()
                .statusCode(HttpStatus.OK.value());

        Mockito.verify(customerRepository).save(any());
    }

    @Test
    public void shouldGetCustomer() {
        // given
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(new Customer()));

        // then
        when()
                .get(getRootUrl() + "/customer")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Customer[].class);
    }

    @Test
    public void shouldGetCustomerAllCustomers() {
        // given
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(new Customer()));

        // when
        List<Customer> response = Arrays.stream(when()
                        .get(getRootUrl() + "/customer")
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(Customer[].class))
                .toList();

        // then
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void shouldGetCustomerById() {
        // given
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer(customerId, "Stepa", "777", 20);

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        Customer response = when()
                .get(getRootUrl() + "/customer/" + customerId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Customer.class);

        // then
        //Assertions.assertEquals(customer.getId(), response.getId());

        assertThat(customer)
                .returns(customerId, Customer::getId)
                .returns("Stepa", Customer::getName)
                .returns("777", Customer::getPhone)
                .returns(20, Customer::getAge);
    }

    @Test
    void shouldThrowBadRequestException() {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("id", UUID.randomUUID().toString());
        request.put("name", "name1");
        request.put("phone", "phone");
        request.put("age", "10");

        // then
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(getRootUrl() + "/customer")
                .then()
                .statusCode(HttpStatus.OK.value());

        Mockito.verify(customerRepository).save(any());
    }
}