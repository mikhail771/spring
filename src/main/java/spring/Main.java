package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.config.AppConfig;
import spring.model.User;
import spring.service.UserService;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        for (int i = 0; i < 4; i++) {
            userService.add(new User("user" + i, "Lastname" + i,
                    "user" + i + "gmail.com", "password" + i));
        }
        userService.listUsers().forEach(System.out::println);
    }
}
