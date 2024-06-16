package com.company.hr_crm.security;

import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.entity.Vacancy;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "JunHR row", code = JunHRRowRole.CODE)
public interface JunHRRowRole {
    String CODE = "jun-hr-row";

    @JpqlRowLevelPolicy(entityClass = Candidate.class, where = "{E}.vacancy.author.id = :current_user_id")
    void candidate();

    @JpqlRowLevelPolicy(entityClass = Vacancy.class, where = "{E}.author.id = :current_user_id")
    void vacancy();
}