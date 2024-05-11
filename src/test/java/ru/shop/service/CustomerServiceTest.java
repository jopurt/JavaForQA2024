package ru.shop.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

class CustomerServiceTest {

    private final CustomerRepository repository = Mockito.mock();
    private final CustomerService customerService = new CustomerService(repository);

    @Test
    public void shouldGetCustomer() {
        // given
        UUID customerId= UUID.randomUUID();
        Customer mockedCustomer=new Customer();
        Mockito.when(repository.findById(customerId)).thenReturn(Optional.of(mockedCustomer));

        // when
        Customer customer = customerService.getById(customerId);

        // then

        //Было
        //Assertions.assertEquals(mockedCustomer, customer);

        //Стало
        assertThat(customer).isEqualTo(mockedCustomer);

    }

    @Test
    public void shouldThrowWhenCustomerNotFound() {
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
    public void shouldSaveCustomer() {
        // given
        Customer customer= new Customer();
        // when
        customerService.save(customer);

        // then
        Mockito.verify(repository, Mockito.times(1)).save(customer);

    }

    @Test
    public void shouldUseRepositoryFindAllWhenCallFindAllCustomers() {
        // given

        // when
        customerService.findAll();

        // then
        Mockito.verify(repository.findAll());

    }


}