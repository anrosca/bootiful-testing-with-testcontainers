package inc.evil.clinic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MedAssistApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedAssistApplication.class, args);
    }
}
