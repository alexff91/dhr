package com.dhr.services;

import com.dhr.model.QuestionSkill;
import com.dhr.model.Skill;
import com.dhr.model.Vacancy;
import com.dhr.model.enums.VacancyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class VacanciesServiceImpl implements VacancyService {
    @Autowired
    private VacancyService repository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private QuestionSkillService questionSkillService;

    @Override
    public String save(Vacancy vacancy) {
        vacancy.setId(Integer.toHexString(vacancy.hashCode()) + Long.toHexString(new Date().getTime()));
        String video = vacancy.getVideo();
        if (video != null && !video.isEmpty())
            replaceYoutubeVideoPath(vacancy, video);
        vacancy.getQuestions().forEach(question -> {
            Set<QuestionSkill> questionSkills = new HashSet<>();
            question.getSkills().forEach(skill -> {
                Skill companySkill = Skill.builder().company(vacancy.getCompany()).name(skill.getName()).build();
                skillService.save(companySkill);
                questionSkills.add(questionSkillService.save(skill));
            });
            question.getSkills().clear();
            question.getSkills().addAll(questionSkills);
            question.setVacancy(vacancy);
        });
        repository.save(vacancy);
        return vacancy.getId();
    }

    private void replaceYoutubeVideoPath(Vacancy vacancy, String video) {
        String replaced = video.replace("youtu.be", "www.youtube.com");
        vacancy.setVideo(
                replaced.contains("/embed/") ?
                        replaced :
                        (replaced.substring(0, replaced.lastIndexOf("/"))
                                + "/embed"
                                + replaced.substring(replaced.lastIndexOf("/"), replaced.length())
                        ));
    }

    @Override
    public void increaseViewCounter(String vacancyId) {
        Vacancy vacancy = repository.get(vacancyId).get();
        vacancy.setViewsCount(vacancy.getViewsCount() + 1);
        repository.save(vacancy);
    }

    @Override
    public void delete(Vacancy vacancy) {
        vacancy.setDeleted(true);
        repository.save(vacancy);
    }

    @Override
    public void update(String vacancyId, Vacancy vacancy) {
        Vacancy oldVacancy = repository.get(vacancyId).get();
        oldVacancy.setDescription(vacancy.getDescription());
        oldVacancy.setPosition(vacancy.getPosition());
        String video = vacancy.getVideo();
        if (video != null && !video.isEmpty())
            replaceYoutubeVideoPath(oldVacancy, video);
        oldVacancy.setImg(vacancy.getImg());
        vacancy.getQuestions().forEach(question -> {
            question.setVacancy(oldVacancy);
            Set<QuestionSkill> skills = new HashSet<>();
            question.getSkills().forEach(skill -> {
                        Skill companySkill = Skill.builder().company(oldVacancy.getCompany()).name(skill.getName()).build();
                        skillService.save(companySkill);
                        skills.add(questionSkillService.save(skill));
                    }
            );
            question.getSkills().clear();
            question.getSkills().addAll(skills);
        });
        oldVacancy.getQuestions().clear();
        oldVacancy.getQuestions().addAll(vacancy.getQuestions());
        oldVacancy.setUpdateDate(new Date());
        repository.save(oldVacancy);
    }

    @Override
    public Optional<Vacancy> get(String id) {
        return repository.get(id);
    }

    @Override
    public Iterable<Vacancy> getAll() {
        return repository.getAll();
    }

    @Override
    public void updateRespondCount(Vacancy respondVacancy) {
        respondVacancy.setUnansweredRespondsCount(respondVacancy.getUnansweredRespondsCount() - 1);
        repository.save(respondVacancy);
    }

    @Override
    public void restore(String vacancyId) {
        Vacancy oldVacancy = repository.get(vacancyId).get();
        oldVacancy.setDeleted(false);
        repository.save(oldVacancy);
    }

    @Override
    public void copy(String vacancyId) {
        Vacancy oldVacancy = repository.get(vacancyId).get();
        oldVacancy.setId(null);
        oldVacancy.getQuestions().forEach(question -> question.setId(null));
        oldVacancy.setRespondsCount(0L);
        oldVacancy.setStatus(VacancyStatus.IN_WORK);
        oldVacancy.setUnansweredRespondsCount(0L);
        oldVacancy.setUpdateDate(new Date());
        oldVacancy.setCreationDate(new Date());
        oldVacancy.setResponds(new HashSet<>());
        oldVacancy.setViewsCount(0L);
        save(oldVacancy);
    }
}
