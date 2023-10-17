package br.com.freitas.orders.config;

import br.com.freitas.orders.entities.*;
import br.com.freitas.orders.entities.enums.OrderStatus;
import br.com.freitas.orders.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;

/**
 * @author Edson da Silva Freitas
 * {@code @created} 18/08/2023
 * {@code @project} orders
 */
@Configuration
@Profile({"test", "dev"})
public class TestConfig implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {

        Category c1 = new Category(null, "Electronics");
        Category c2 = new Category(null, "Books");
        Category c3 = new Category(null, "Computers");
        categoryRepository.saveAll(List.of(c1, c2, c3));

        Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
        Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
        Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
        Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
        Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");
        productRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        p1.addCategories(c2);
        p2.addCategories(c1);
        p2.addCategories(c3);
        p3.addCategories(c3);
        p4.addCategories(c3);
        p5.addCategories(c2);
        productRepository.saveAll(List.of(p1, p2, p3, p4, p5));

        String senhaDefault = "123@UmaSenha"; //Ambiente de laboratorio

        User u1 = new User(null, "Edson Freitas", "edson@example.com", "9999-9999", "user", passwordEncoder.encode(senhaDefault), 1);
        User u2 = new User(null, "Alex Green", "alex@example.com", "9999-9999", "alex", passwordEncoder.encode(senhaDefault), 1);
        User u3 = new User(null, "Zack Abanton", "abanton@example.com", "8888-8888", "zack", passwordEncoder.encode(senhaDefault), 2);
/*        User u4 = new User(null, "Jane Smith", "jane@example.com", "7777-7777", "jane",passwordEncoder.encode(senhaDefault), 3);
        User u5 = new User(null, "Michael Johnson", "michael@example.com", "6666-6666","michael", passwordEncoder.encode(senhaDefault), 1);
        User u6 = new User(null, "Emily Davis", "emily@example.com", "5555-5555", "emily",passwordEncoder.encode(senhaDefault), 1);
        User u7 = new User(null, "David Wilson", "david@example.com", "4444-4444", "david",passwordEncoder.encode(senhaDefault), 2);
        User u8 = new User(null, "Linda Lee", "linda@example.com", "3333-3333", "linda",passwordEncoder.encode(senhaDefault), 3);
        User u9 = new User(null, "William Taylor", "william@example.com", "2222-2222", "william",passwordEncoder.encode(senhaDefault), 1);
        User u10 = new User(null, "Jennifer Miller", "jennifer@example.com", "1111-1111", "jennifer",passwordEncoder.encode(senhaDefault), 2);
        User u11 = new User(null, "Xavier Miller", "xavier@example.com", "1111-1111", "xavier",passwordEncoder.encode(senhaDefault), 3);*/
        userRepository.saveAll(List.of(u1, u2, u3)); //, u4, u5, u6, u7, u8, u9, u10, u11));

        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, u1); //Formato sera formatado pela anotação @JsonFormat
        orderRepository.saveAll(List.of(o1, o2, o3));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
        orderItemRepository.saveAll(List.of(oi1, oi2, oi3, oi4));

        Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
        Payment pay2 = new Payment(null, Instant.parse("2022-08-25T22:13:19Z"), o2);
        o1.setPayment(pay1);
        o2.setPayment(pay2);
        o1.setTotal(o1.getTotal());
        o2.setTotal(o2.getTotal());
        o3.setTotal(o3.getTotal());
        orderRepository.saveAll(List.of(o1, o2, o3));
    }
}