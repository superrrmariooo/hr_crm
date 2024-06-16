package com.company.hr_crm.app;

import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.entity.User;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("restServiceBean")
public class RESTServiceBean {

    @Autowired
    private DataManager dataManager;

    public int countCandidatesBetween (int from, int to) {
        return dataManager.load(Candidate.class)
                .query("e.age >= ?1 and e.age <= ?2", from, to)
                .list()
                .size();
    }

}