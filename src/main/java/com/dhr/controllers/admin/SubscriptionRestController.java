package com.dhr.controllers.admin;

import com.dhr.model.Company;
import com.dhr.model.Subscription;
import com.dhr.services.CompanyService;
import com.dhr.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionRestController {
    @Autowired
    SubscriptionService subscriptionsService;

    @Autowired
    CompanyService companyService;

    @PostMapping("/company/{companyId}")
    public ResponseEntity createSubscription(@RequestBody Subscription subscription, @PathVariable Long companyId) {
        subscriptionsService.save(subscription);
        Company company = companyService.get(companyId).get();
        company.setSubscription(subscription);
        companyService.update(company);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable Long companyId) {
        Company company = companyService.get(companyId).get();
        return new ResponseEntity<>(company.getSubscription(),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateSubscription(@RequestBody Subscription subscription) {
        Subscription oldSub = subscriptionsService.get(subscription.getId()).get();
        oldSub.setEndDate(subscription.getEndDate());
        oldSub.setType(subscription.getType());
        subscriptionsService.save(oldSub);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public Iterable<Subscription> getSubscriptions() {
        return subscriptionsService.getAll();
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity deleteSubscription(@PathVariable Long subscriptionId) {
        subscriptionsService.delete(subscriptionsService.get(subscriptionId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
