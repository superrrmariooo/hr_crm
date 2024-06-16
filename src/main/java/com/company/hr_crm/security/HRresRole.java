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

@ResourceRole(name = "HR resource", code = HRresRole.CODE, scope = "UI")
public interface HRresRole {
    String CODE = "hr-res";

    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void fullAccess();

    @MenuPolicy(menuIds = {"Vacancy.list", "Candidate.list"})
    @ViewPolicy(viewIds = {"inputDialog", "LoginView", "MainView", "Vacancy.list", "Vacancy.detail", "Candidate.detail", "Candidate.list", "User.list", "headerPropertyFilterLayout", "flowui_AddConditionView", "flowui_GroupFilterCondition.detail", "flowui_JpqlFilterCondition.detail", "flowui_PropertyFilterCondition.detail"})
    void screens();

    @SpecificPolicy(resources = {"ui.loginToUi", "ui.genericfilter.modifyJpqlCondition", "ui.genericfilter.modifyConfiguration", "ui.genericfilter.modifyGlobalConfiguration"})
    void specific();

    @EntityAttributePolicy(entityClass = Candidate.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Candidate.class, actions = EntityPolicyAction.READ)
    void candidate();

    @EntityAttributePolicy(entityClass = Comment.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Comment.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
    void comment();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @EntityAttributePolicy(entityClass = Vacancy.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Vacancy.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void vacancy();
}