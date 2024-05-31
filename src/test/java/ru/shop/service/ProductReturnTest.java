package ru.shop.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.shop.exception.BadOrderCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.ProductReturnRepository;
import ru.shop.service.ProductReturnService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductReturnTest {

    private final ProductReturnRepository productReturnRepository = Mockito.mock(ProductReturnRepository.class);
    private final ProductReturnService productReturnService = new ProductReturnService(productReturnRepository);

    @LocalServerPort
    private int port;

    @Test
    void shouldAddProductReturnSuccessfully() {
        UUID orderId = UUID.randomUUID();
        long orderCount = 10;
        long returnCount = 5;
        Order order = new Order(orderId, UUID.randomUUID(), UUID.randomUUID(), orderCount, 100);
        ProductReturn productReturn = new ProductReturn(UUID.randomUUID(), orderId, LocalDate.now(), (int) returnCount);
        when(productReturnRepository.save(any(ProductReturn.class))).thenReturn(productReturn);

        productReturnService.add(order, returnCount);

        verify(productReturnRepository, times(1)).save(any(ProductReturn.class));
    }

    @Test
    void shouldGetProductReturnAllProductsReturn() {
        when(productReturnRepository.findAll()).thenReturn(List.of(new ProductReturn()));

        List<ProductReturn> result = productReturnService.findAll();

        assertFalse(result.isEmpty());
        verify(productReturnRepository, times(1)).findAll();
    }

    @Test
    void shouldGetProductReturn() {
        UUID productReturnId = UUID.randomUUID();
        ProductReturn productReturn = new ProductReturn(productReturnId, UUID.randomUUID(), LocalDate.now(), 5);
        when(productReturnRepository.findById(productReturnId)).thenReturn(Optional.of(productReturn));

        ProductReturn result = productReturnService.findById(productReturnId);

        assertNotNull(result);
        assertEquals(productReturnId, result.getId());
        verify(productReturnRepository, times(1)).findById(productReturnId);
    }

    @Test
    void shouldThrowBadOrderCountException() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, 100);
        long count = 10000;

        assertThrows(BadOrderCountException.class, () -> productReturnService.add(order, count));
        verify(productReturnRepository, never()).save(any(ProductReturn.class));
    }

    @Test
    void shouldThrowEntityNotFoundException() {
        UUID productReturnId = UUID.randomUUID();
        when(productReturnRepository.findById(productReturnId)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> productReturnService.findById(productReturnId));
        verify(productReturnRepository, times(1)).findById(productReturnId);
    }
}
