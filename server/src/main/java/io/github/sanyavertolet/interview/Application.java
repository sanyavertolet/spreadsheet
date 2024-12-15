package io.github.sanyavertolet.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Spring Boot application.
 *
 * <p>This class is annotated with {@link SpringBootApplication}, which is a convenience annotation
 * that combines the functionality of {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration},
 * {@link org.springframework.context.annotation.ComponentScan}, and {@link org.springframework.context.annotation.Configuration}.
 * It enables autoconfiguration, component scanning, and Java-based configuration for the application.</p>
 *
 * <p>The {@code main} method uses {@link SpringApplication#run(Class, String...)} to bootstrap the application,
 * starting the embedded web server and initializing the Spring context.</p>
 */
@SpringBootApplication
public class Application {

    /**
     * The main method that serves as the entry point for the application.
     *
     * <p>This method starts the Spring Boot application by invoking {@link SpringApplication#run(Class, String...)},
     * which initializes the Spring ApplicationContext and starts the embedded server (if applicable).</p>
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
