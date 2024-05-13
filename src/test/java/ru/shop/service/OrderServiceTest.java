package ru.shop.service;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.BadOrderCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class OrderServiceTest {
    private final OrderRepository repository = Mockito.mock();
    private final OrderService orderService = new OrderService(repository);

    @Test
    void shouldThrowWhenProductNotFound() {
        // then
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> orderService.getById(UUID.randomUUID())
        );
    }

    private final OrderService service = new OrderService(repository);

    @Test
    void shouldAddOrder() {
        // given
        Customer customer = new Customer(UUID.randomUUID(),"Kolya","111",22);
        Product product = new Product(UUID.randomUUID(),"Pivo",250, ProductType.GOOD);

        // when
        service.add(customer, product, 100);

        // then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.captor();

        Mockito.verify(repository).save(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();

        Assertions.assertEquals(100,savedOrder.getCount());
        Assertions.assertEquals(customer.getId(),savedOrder.getCustomerId());
        Assertions.assertEquals(product.getId(),savedOrder.getProductId());



        //verify(repository).save(any());
    }

    @Test
    void shouldThrowBadOrderCountExceptionWhenCountIsLessOrEqualsThanZero() {
        // given
        Customer customer = new Customer();
        Product product = new Product();

        // then
        //Ноль
        Assertions.assertThrows(
                BadOrderCountException.class,
                () -> service.add(customer, product, 0)
        );

        //Меньше ноля
        Assertions.assertThrows(
                BadOrderCountException.class,
                () -> service.add(customer, product, -100)
        );
    }

    @DisplayName("Если фигня в count")
    @ParameterizedTest
    @ValueSource(ints = {0,-1,Integer.MIN_VALUE,Integer.MAX_VALUE})
    void withParameter(Integer count) {
        // given
        Customer customer = new Customer();
        Product product = new Product();

        // then
        Assertions.assertThrows(
                BadOrderCountException.class,
                () -> service.add(customer, product,count)
        );

    }

    @Test
    public void shouldFindByCustomer() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId, "name", "phone", 20
        );

        Order customerOrder = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 10, 10
        );
        Order randomCustomerOrder = new Order(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, 10
        );

        Mockito.when(repository.findAll())
                .thenReturn(
                        List.of(customerOrder, randomCustomerOrder)
                );

        // when
        List<Order> Result = service.findByCustomer(customer);

        // then
        Assertions.assertEquals(1, Result.size());
        Assertions.assertEquals(customerOrder, Result.get(0));
    }

    @Test
    public void shouldGetTotalCustomerAmount() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId, "Mama", "123", 20
        );

        Order customerOrder1 = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 10, 70
        );
        Order customerOrder2 = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 100, 20
        );
        Order randomCustomerOrder = new Order(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 1, 10
        );
        Order negativeRandomCustomerOrder = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 1111, -10
        );

        Mockito.when(repository.findAll())
                .thenReturn(
                        List.of(customerOrder1, customerOrder2, randomCustomerOrder,negativeRandomCustomerOrder)
                );

        // when
        long result = service.getTotalCustomerAmount(customer);

        // then
        Assertions.assertEquals(90, result);
    }


}
