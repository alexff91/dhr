package com.dhr;

import com.dhr.model.Company;
import com.dhr.model.User;
import com.dhr.services.CompanyService;
import com.dhr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    void initAdminUser() {
        User admin = userService.getByLogin("admin");
        if (admin == null) {
            Company company = companyService.save(new Company());
            User adminUser = User.builder().company(company).login("admin").password("admin123").build();
            userService.save(company.getId(), adminUser);
        }
    }
}
