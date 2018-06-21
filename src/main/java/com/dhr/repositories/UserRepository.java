package com.dhr.repositories;

import com.dhr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByCompanyId(Long companyId);

    User findByLogin(String userName);
}