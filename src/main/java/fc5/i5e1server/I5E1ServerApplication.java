package fc5.i5e1server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class I5E1ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(I5E1ServerApplication.class, args);
    }

}
