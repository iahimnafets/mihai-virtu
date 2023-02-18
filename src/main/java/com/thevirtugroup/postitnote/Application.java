package com.thevirtugroup.postitnote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.config.MvcConfig;
import com.thevirtugroup.postitnote.config.WebSecurityConfig;
import com.thevirtugroup.postitnote.model.User;
import com.thevirtugroup.postitnote.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 */
@EnableSwagger2
@SpringBootApplication
@Import({MvcConfig.class, WebSecurityConfig.class})
@ComponentScan({
        "com.thevirtugroup.postitnote.service",
        "com.thevirtugroup.postitnote.security",
        "com.thevirtugroup.postitnote.repository",
        "com.thevirtugroup.postitnote.rest"
})
@Slf4j
public class Application {
    public static void main(String[] args) throws Exception {
        System.setProperty("spring.devtools.restart.enabled", "true");

        SpringApplication.run(Application.class, args);
    }
    @Bean
    CommandLineRunner run(UserRepository userService, PasswordEncoder passwordEncoder ) {
        log.info("Add 3 users for test ");
        return args -> {

            int index = 1;
            log.info( "#######     use this users for your test    ####### " );
            while ( index < 4){
                User user = new User( new Long(1) , "mihai"+index ,  "mihai"+index, "Mihai Stefan "+ index );
                // The password must be encrypted before to save in the Data-Base
                log.info( "    -->  UserName: " +  user.getUsername() +" password: " + user.getPassword() );
                user.setPassword( passwordEncoder.encode(user.getPassword()) );
                userService.saveUser(user);
                index++;
            }

        };
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
