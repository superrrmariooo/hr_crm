package com.company.hr_crm.app;

import com.company.hr_crm.entity.Candidate;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class CandidateRepository {

    @Autowired
    private DataManager dataManager;

    public List<Candidate> getCandidates(LoadContext<Candidate> loadContext) {
        return dataManager.loadList(loadContext);
    }

    public void deleteCandidates(Collection<Candidate> collection) {
        for (Candidate candidate : collection) {
            dataManager.remove(candidate);
        }
    }

    public Set<Object> saveCandidate(SaveContext saveContext) {
        return dataManager.save(saveContext);
    }

}