package com.company.hr_crm.app;

import com.company.hr_crm.entity.User;
import com.company.hr_crm.entity.Vacancy;
import com.company.hr_crm.entity.VacancyStatus;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.StandardDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    public List<Vacancy> getVacancies(LoadContext<Vacancy> loadContext) {
        return vacancyRepository.getVacancies(loadContext);
    }

    public Set<Object> saveVacancy(SaveContext saveContext) {
        return vacancyRepository.saveVacancy(saveContext);
    }

    public void setVacancyStatusAndAuthorOnInit(StandardDetailView.InitEntityEvent<Vacancy> event) {
        Vacancy vacancy = event.getEntity();
        vacancy.setVacancyStatus(VacancyStatus.ACTIVE);
        vacancy.setAuthor((User) currentAuthentication.getUser());
    }
}