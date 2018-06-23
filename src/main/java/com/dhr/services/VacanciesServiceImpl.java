package com.dhr.services;

import com.dhr.model.Skill;
import com.dhr.model.Vacancy;
import com.dhr.repositories.VacancyRepository;
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
    private VacancyRepository repository;

    @Autowired
    private SkillService skillService;

    @Override
    public String save(Vacancy vacancy) {
        vacancy.setId(Integer.toHexString(vacancy.hashCode()) + Long.toHexString(new Date().getTime()));
        String video = vacancy.getVideo();
        if (video != null)
            replaceYoutubeVideoPath(vacancy, video);
        vacancy.getQuestions().forEach(question -> {
            Set<Skill> questionSkills = new HashSet<>();
            question.getSkills().forEach(skill -> {
                skill.setCompany(vacancy.getCompany());
                questionSkills.add(skillService.save(skill));
            });
            question.getSkills().clear();
            question.getSkills().addAll(questionSkills);
            question.setVacancy(vacancy);
        });
        repository.save(vacancy);
        return vacancy.getId();
    }

    private void replaceYoutubeVideoPath(Vacancy vacancy, String video) {
        vacancy.setVideo(
                video.contains("/embed/") ?
                        video :
                        (video.substring(0, video.lastIndexOf("/"))
                                + "/embed"
                                + video.substring(video.lastIndexOf("/"), video.length())
                        ).replace("youtu.be", "www.youtube.com"));
    }

    @Override
    public void increaseViewCounter(String vacancyId) {
        Vacancy vacancy = repository.findById(vacancyId).get();
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
        Vacancy oldVacancy = repository.findById(vacancyId).get();
        oldVacancy.setDescription(vacancy.getDescription());
        oldVacancy.setPosition(vacancy.getPosition());
        String video = vacancy.getVideo();
        if (video != null)
            replaceYoutubeVideoPath(oldVacancy, video);
        oldVacancy.setImg(vacancy.getImg());
        vacancy.getQuestions().forEach(question -> {
            question.setVacancy(oldVacancy);
            Set<Skill> skills = new HashSet<>();
            question.getSkills().forEach(skill -> {
                        skill.setCompany(oldVacancy.getCompany());
                        Skill savedSkill = skillService.save(skill);
                        skills.add(savedSkill);
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
        return repository.findById(id);
    }

    @Override
    public Iterable<Vacancy> getAll() {
        return repository.findAll();
    }

    @Override
    public void updateRespondCount(Vacancy respondVacancy) {
        respondVacancy.setUnansweredRespondsCount(respondVacancy.getUnansweredRespondsCount() - 1);
        repository.save(respondVacancy);
    }
}
