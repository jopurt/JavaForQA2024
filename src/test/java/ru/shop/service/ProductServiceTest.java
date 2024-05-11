package ru.shop.service;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Product;
import ru.shop.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

public class ProductServiceTest {
    private final ProductRepository repository = Mockito.mock();
    private final ProductService productService = new ProductService(repository);

    @Test
    public void shouldThrowWhenProductNotFound() {
        // then
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> productService.getById(UUID.randomUUID())
        );
    }

}
