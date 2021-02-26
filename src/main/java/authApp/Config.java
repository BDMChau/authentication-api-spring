package authApp;

import authApp.Middleware.verifyToken;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.NoSuchAlgorithmException;

@Configuration
public class Config {


    @Bean
    public FilterRegistrationBean<verifyToken> verifyToken(){
        FilterRegistrationBean<verifyToken> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new verifyToken());
        registrationBean.addUrlPatterns("/api/user/*");

        return registrationBean;
    }

    @Bean
    public void welcome() throws NoSuchAlgorithmException {
        System.out.println("Hello");
    }
}
