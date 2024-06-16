package com.company.hr_crm.app;

import com.company.hr_crm.entity.Vacancy;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;

@Component
public class VacancyRepository {

    @Autowired
    private DataManager dataManager;

    public List<Vacancy> getVacancies(LoadContext<Vacancy> loadContext) {
        return dataManager.loadList(loadContext);
    }

    public Set<Object> saveVacancy(SaveContext saveContext) {
        return dataManager.save(saveContext);
    }

}