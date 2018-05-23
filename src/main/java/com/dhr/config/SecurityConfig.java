//package com.dhr.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    PropertiesConfig config;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
//        authenticationMgr.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password("pass").authorities("ROLE_USER").and()
//                .passwordEncoder(passwordEncoder()).withUser("admin").password("admin").authorities("ROLE_ADMIN");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*");
//            }
//        };
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
//
//        Path path = Paths.get(config.getRecordingsPath());
//
//        if (!Files.exists(path)) {
//            log.info("Folder '{}' does not exist. Creating folder", path);
//            path = Files.createDirectories(path);
//            log.info("Created folder '{}'", path.toAbsolutePath().toString());
//        }
//
//        Files.createDirectories(Paths.get(path + "/user"));
//        Files.createDirectories(Paths.get(path + "/admin"));
//
//        http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().httpBasic().and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/recording").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/v1/recording/**").authenticated();
//
//    }
//
//}