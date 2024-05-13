package ru.shop.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class CustomerServiceTest {

    private final CustomerRepository repository = mock();
    private final CustomerService customerService = new CustomerService(repository);

    @Test
    void shouldGetCustomer() {
        // given
        UUID customerId= UUID.randomUUID();
        Customer mockedCustomer=new Customer();
        when(repository.findById(customerId)).thenReturn(Optional.of(mockedCustomer));

        // when
        Customer customer = customerService.getById(customerId);

        // then

        //Было
        //Assertions.assertEquals(mockedCustomer, customer);

        //Стало
        assertThat(customer).isEqualTo(mockedCustomer);

    }

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // then
        //Было
        //Assertions.assertThrows(
        //        EntityNotFoundException.class,
        //        () -> customerService.getById(UUID.randomUUID())
        //);

        //Стало
        assertThatThrownBy(() -> customerService.getById(UUID.randomUUID())).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldSaveCustomer() {
        // given
        Customer customer= new Customer();
        // when
        customerService.save(customer);

        // then
        verify(repository, Mockito.times(1)).save(customer);

    }


    @Test
    void shouldUseRepositoryFindAllWhenCallFindAllCustomers2() {
        //given

        UUID customerId1 = UUID.randomUUID();
        UUID customerId2 = UUID.randomUUID();

        Customer customer1 = new Customer(customerId1, "Dima", "790010201", 10);
        Customer customer2 = new Customer(customerId2, "Vasya", "994930", 45);

        //when

        when(repository.findAll()).thenReturn(List.of(customer1, customer2));

        // вызов метода у customerService
        List<Customer> customers = customerService.findAll();

        //then

        //вызвался ли метод у репозитория
        verify(repository).findAll();

        // соответствие количества
        assertEquals(2, customers.size());
    }

}