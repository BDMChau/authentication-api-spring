package authApp;

import Helper.HashingSHA512;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.NoSuchAlgorithmException;

@Configuration
public class Config {

    @Bean
    public void welcome() throws NoSuchAlgorithmException {
        System.out.println("WELCOME TO MY APPLICATION!");

        String originalString = "Chauuuu";
        String password = "Chauuuu";

        String hashOriginal = new HashingSHA512().hash(originalString);
        String passwordUser = new HashingSHA512().hash(password);



        Boolean compare = new HashingSHA512().compare(password, hashOriginal);


    }
}
