package com.company.hr_crm.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Full Access", code = FullAccessRole.CODE)
public interface FullAccessRole {

    String CODE = "system-full-access";

    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ViewPolicy(viewIds = {"User.list", "Vacancy.list", "Candidate.list", "Comment_.list", "sec_ResourceRoleModel.list", "sec_RowLevelRoleModel.list", "entityInfoView", "datatl_entityInspectorDetailView", "flowui_PropertyFilterCondition.detail", "flowui_JpqlFilterCondition.detail", "flowui_AddConditionView", "flowui_GroupFilterCondition.detail", "headerPropertyFilterLayout", "inputDialog", "multiValueSelectDialog", "sec_ResourceRoleModel.detail", "sec_ResourceRoleModel.lookup", "sec_RowLevelRoleModel.detail", "sec_RowLevelRoleModel.lookup", "resetPasswordView", "changePasswordView", "sec_EntityAttributeResourcePolicyModel.detail", "sec_EntityResourcePolicyModel.detail", "sec_ViewResourcePolicyModel.detail", "sec_GraphQLResourcePolicyModel.detail", "sec_MenuResourcePolicyModel.detail", "sec_ViewResourcePolicyModel.create", "sec_MenuResourcePolicyModel.create", "sec_ResourcePolicyModel.detail", "sec_EntityAttributeResourcePolicyModel.create", "sec_SpecificResourcePolicyModel.detail", "sec_EntityResourcePolicyModel.create", "roleAssignmentView", "sec_RowLevelPolicyModel.detail", "sec_UserSubstitution.detail", "sec_UserSubstitution.view", "MainView", "User.detail", "LoginView", "Vacancy.detail", "Candidate.detail", "Comment_.detail"})
    @MenuPolicy(menuIds = {"User.list", "Vacancy.list", "Candidate.list"})
    @SpecificPolicy(resources = "*")
    void fullAccess();
}