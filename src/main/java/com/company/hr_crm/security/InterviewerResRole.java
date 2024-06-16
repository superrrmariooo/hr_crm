package com.company.hr_crm.security;

import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.entity.Comment;
import com.company.hr_crm.entity.User;
import com.company.hr_crm.entity.Vacancy;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Interviewer resource", code = InterviewerResRole.CODE, scope = "UI")
public interface InterviewerResRole {
    String CODE = "interviewer-res";

    @MenuPolicy(menuIds = {"Vacancy.list", "Candidate.list"})
    @ViewPolicy(viewIds = {"Vacancy.list", "Candidate.list", "Candidate.detail", "Vacancy.detail", "inputDialog", "LoginView", "MainView", "headerPropertyFilterLayout", "flowui_AddConditionView", "flowui_GroupFilterCondition.detail", "flowui_JpqlFilterCondition.detail", "flowui_PropertyFilterCondition.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Candidate.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Candidate.class, actions = EntityPolicyAction.READ)
    void candidate();

    @EntityAttributePolicy(entityClass = Comment.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Comment.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
    void comment();

    @EntityAttributePolicy(entityClass = Vacancy.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Vacancy.class, actions = EntityPolicyAction.READ)
    void vacancy();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @SpecificPolicy(resources = {"ui.loginToUi", "ui.genericfilter.modifyConfiguration", "ui.genericfilter.modifyJpqlCondition", "ui.genericfilter.modifyGlobalConfiguration"})
    void specific();
}