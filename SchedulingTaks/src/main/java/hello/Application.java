package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//an application that prints out the current time every five seconds using Spring’s @Scheduled annotation
@SpringBootApplication
@EnableScheduling  //creates the background task executor
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }
}