package com.dhr.repositories;

import com.dhr.model.Respond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondRepository extends JpaRepository<Respond, String> {
    List<Respond> findAllByVacancyId(String vacancyId);

    List<Respond> findAllByVacancyIdAndId(String vacancyId, String id);
}