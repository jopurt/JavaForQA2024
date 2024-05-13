package ru.shop.service;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.model.Product;
import ru.shop.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private final ProductRepository repository = Mockito.mock();
    private final ProductService productService = new ProductService(repository);


    @Test
    void shouldThrowWhenProductNotFound() {
        // then
        //Было
//        Assertions.assertThrows(
//                EntityNotFoundException.class,
//                () -> productService.getById(UUID.randomUUID())
//        );
        //Стало
        assertThatThrownBy(() -> productService.getById(UUID.randomUUID())).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("Сервис должен загружать продукт по id")
    @Test
    void shouldGetCustomer() {
        // given
        UUID customerId= UUID.randomUUID();
        Customer mockedCustomer=new Customer();
        var mockedProduct=new Product();

        Mockito
                .when(repository.findById(customerId))
                .thenReturn(Optional.of(mockedProduct));

        // when
        Product product = productService.getById(customerId);

        // then

        //Было
        //Assertions.assertEquals(mockedProduct, product);

        //Стало
        assertThat(product).isEqualTo(mockedProduct);

    }
}
