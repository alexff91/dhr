package com.dhr.services;

import com.dhr.model.Skill;
import com.dhr.model.Vacancy;
import com.dhr.model.VacancyFunnel;
import com.dhr.model.enums.SkillStatus;
import com.dhr.model.enums.VacancyStatus;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class VacanciesServiceImpl implements VacancyService {
    @Autowired
    private VacancyRepository repository;

    @Autowired
    private SkillService companySkillService;

    @Autowired
    private QuestionSkillService questionSkillService;

    @Autowired
    private FunnelService funnelService;

    @Override
    public String save(Vacancy vacancy) {
        vacancy.setId(Integer.toHexString(vacancy.hashCode()) + Long.toHexString(new Date().getTime()));
        String video = vacancy.getVideo();
        if (video != null && !video.isEmpty())
            replaceYoutubeVideoPath(vacancy, video);
        vacancy.getQuestions().forEach(question -> {
            question.getSkills().forEach(skill -> {
                Skill companySkill = Skill.builder().deleted(false)
                        .status(SkillStatus.ACTIVE)
                        .company(vacancy.getCompany())
                        .name(skill.getName()).build();
                companySkillService.save(companySkill);
                skill.setQuestion(question);
                skill.setDeleted(false);
            });
            question.setVacancy(vacancy);
        });
        Vacancy save = repository.save(vacancy);

        VacancyFunnel funnel = new VacancyFunnel();
        funnel.setVacancy(save);
        VacancyFunnel savedFunnel = funnelService.save(funnel);
        save.setFunnel(savedFunnel);
        repository.save(save);
        return save.getId();
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
        if (video != null && !video.isEmpty())
            replaceYoutubeVideoPath(oldVacancy, video);
        oldVacancy.setImg(vacancy.getImg());
        oldVacancy.getQuestions().forEach(oldQuestion ->
                oldQuestion.getSkills().forEach(oldSkill ->
                        questionSkillService.delete(oldSkill)));
        vacancy.getQuestions().forEach(question -> {
            question.setVacancy(oldVacancy);
            question.setId(null);
            question.getSkills().forEach(skill -> {
                        Skill companySkill = Skill.builder().deleted(false)
                                .status(SkillStatus.ACTIVE)
                                .company(vacancy.getCompany())
                                .name(skill.getName()).build();
                        companySkillService.save(companySkill);
                        skill.setDeleted(false);
                        skill.setId(null);
                        skill.setQuestion(question);
                    }
            );
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

    @Override
    public void restore(String vacancyId) {
        Vacancy oldVacancy = repository.findById(vacancyId).get();
        oldVacancy.setDeleted(false);
        repository.save(oldVacancy);
    }

    @Override
    public void copy(String vacancyId) {
        Vacancy oldVacancy = repository.findById(vacancyId).get();
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
