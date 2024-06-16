package com.company.hr_crm.security;

import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.entity.Vacancy;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Interviewer row", code = InterviewerRowRole.CODE)
public interface InterviewerRowRole {
    String CODE = "interviewer-row";

    @JpqlRowLevelPolicy(entityClass = Vacancy.class, where = "{E}.interviewer.id = :current_user_id")
    void vacancy();


    @JpqlRowLevelPolicy(entityClass = Candidate.class, where = "{E}.vacancy.interviewer.id = :current_user_id")
    void candidate();
}