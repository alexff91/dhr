package com.dhr.controllers.manager;

import com.dhr.model.Role;
import com.dhr.model.User;
import com.dhr.services.RoleService;
import com.dhr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserRestController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getRolesByUserId(@PathVariable Long userId) {
        Optional<User> user = userService.get(userId);
        return user.map(u -> new ResponseEntity<>(roleService.getByUserId(userId), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<User> getUserFromSession() {
        Optional<User> user = userService.get(1L);
        return user.map(u -> new ResponseEntity<>(u, OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        Optional<User> user = userService.get(userId);
        if (user.isPresent()) {
            userService.delete(user.get());
            return new ResponseEntity(OK);
        }
        return new ResponseEntity(NOT_FOUND);
    }

    @GetMapping("/roles")
    public Iterable<Role> getRoles() {
        return roleService.getAll();
    }

    @GetMapping()
    public Iterable<User> getUsers() {
        return userService.getAll();
    }
}