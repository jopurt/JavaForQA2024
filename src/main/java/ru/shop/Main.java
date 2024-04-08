package ru.shop;

import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductRepository;
import ru.shop.service.ProductService;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        ProductService productService = new ProductService(
                new ProductRepository()
        );

        CustomerRepository customerRepository = new CustomerRepository();

        OrderRepository orderRepository = new OrderRepository();

        Product ladaKalina = new Product(UUID.randomUUID(), "Lada Kalina", 100, ProductType.GOOD);
        productService.save(ladaKalina);


        Product fordMustang = new Product(UUID.randomUUID(), "Ford Mustang", 10000, ProductType.GOOD);
        productService.save(fordMustang);


        Product carWashing = new Product(UUID.randomUUID(), "Car Washing", 50, ProductType.SERVICE);
        productService.save(carWashing);


        Customer ivan = new Customer(UUID.randomUUID(), "Ivanushka", "123456", 16);
        customerRepository.save(ivan);
        System.out.println(ivan);

        Customer misha = new Customer(UUID.randomUUID(), "Mishgan", "777777", 19);
        customerRepository.save(misha);
        System.out.println(misha);

        Order order1 = new Order(
                UUID.randomUUID(),
                ivan.getId(),
                ladaKalina.getId(),
                2,
                200
        );
        orderRepository.save(order1);
        System.out.println(order1);

        Order order2 = new Order(
                UUID.randomUUID(),
                ivan.getId(),
                fordMustang.getId(),
                3,
                300
        );
        orderRepository.save(order2);
        System.out.println(order2);

        for (Customer customer : customerRepository.findAll()){
            System.out.println(customer);
        }

        for (Product product : productService.findAll()){
            System.out.println(product);
        }

        for (Order order : orderRepository.findAll()){
            System.out.println(order);
        }

        System.out.println("-- All PRODUCTS --");
        for (Product product : productService.findAll()){
            System.out.println(product);
        }
        System.out.println("-- All SERVECIS --");
        for (Product product : productService.findByProductType(ProductType.SERVICE)){
            System.out.println(product);
        }
        System.out.println("-- All GOOD --");
        for (Product product : productService.findByProductType(ProductType.GOOD)){
            System.out.println(product);
        }

    }

}
