package com.dhr.controllers.admin;

import com.dhr.model.Company;
import com.dhr.model.Role;
import com.dhr.model.User;
import com.dhr.model.admin.RegistrationData;
import com.dhr.model.enums.RoleName;
import com.dhr.services.CompanyService;
import com.dhr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/registration")
public class RegistrationRestController {
    private final
    UserService userService;

    private final
    CompanyService companyService;

    @Autowired
    public RegistrationRestController(UserService userService, CompanyService companyService) {
        this.userService = userService;
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity registerCompany(@RequestBody RegistrationData authData) {
        User admin = userService.getByLogin(authData.getUsername());
        if (admin == null) {
            Company company = companyService.save(Company.builder().name(authData.getCompany())
                    .build());
            User adminUser = User.builder()
                    .phone(authData.getPhone())
                    .roles(Collections.singletonList(
                            Role.builder()
                                    .name(RoleName.ADMIN)
                                    .build()))
                    .company(company).login(authData.getUsername()).password(authData.getPassword()).build();
            userService.save(company.getId(), adminUser);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.PRECONDITION_FAILED);
    }
}