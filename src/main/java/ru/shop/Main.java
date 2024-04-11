package ru.shop;

import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.OrderRepository;
import ru.shop.repository.ProductRepository;
import ru.shop.service.CustomerService;
import ru.shop.service.OrderService;
import ru.shop.service.ProductService;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        ProductService productService = new ProductService(
                new ProductRepository()
        );

        CustomerService customerService = new CustomerService(
                new CustomerRepository()
        );

        OrderService orderService = new OrderService(
                new OrderRepository()
        );


        Product ladaKalina = new Product(UUID.randomUUID(), "Lada Kalina", 100, ProductType.GOOD);
        productService.save(ladaKalina);


        Product fordMustang = new Product(UUID.randomUUID(), "Ford Mustang", 10000, ProductType.GOOD);
        productService.save(fordMustang);


        Product carWashing = new Product(UUID.randomUUID(), "Car Washing", 50, ProductType.SERVICE);
        productService.save(carWashing);


        Customer ivan = new Customer(UUID.randomUUID(), "Ivanushka", "123456", 16);
        customerService.save(ivan);


        Customer misha = new Customer(UUID.randomUUID(), "Mishgan", "777777", 19);
        customerService.save(misha);


        orderService.add(misha,fordMustang,2);
        orderService.add(ivan,fordMustang,3);

        try{
            orderService.add(ivan,ladaKalina,0);
        }catch (BadOrderCountException e){
            System.out.println("BadOrderCountException");
        }

        for (Customer customer : customerService.findAll()){
            System.out.println(customer);
        }

        for (Product product : productService.findAll()){
            System.out.println(product);
        }

        for (Order order : orderService.findAll()){
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

        System.out.println("-- Ivan's total cost --");
        System.out.println(orderService.getTotalCustomerAmount(ivan));

        System.out.println("---Ivan orders---");
        for (Order order : orderService.findByCustomer(ivan)){
            System.out.println(order);
        }
        System.out.println("---Misha orders---");
        for (Order order : orderService.findByCustomer(misha)){
            System.out.println(order);
        }

    }

}
