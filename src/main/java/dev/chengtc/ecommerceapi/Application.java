package dev.chengtc.ecommerceapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "E-Commerce API",
                description = "Spring Boot E-Commerce REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name="Ian Cheng",
                        email = "dunz.zheng@gmail.com",
                        url = "https://github.com/chengtc-dev"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url="https://springdoc.org"
                )
        )
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
