package com.company.hr_crm.app;

import com.company.hr_crm.entity.Role;
import com.company.hr_crm.entity.User;
import com.company.hr_crm.security.*;
import io.jmix.core.DataManager;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleProcessing {

    @Autowired
    private DataManager dataManager;

    public void setUserRole(String user, String role) {
        deleteAllUserRoles(user);
        switch (role) {
            case "INTERVIEWER":
                roleSetter(user, InterviewerResRole.CODE, RoleAssignmentRoleType.RESOURCE);
                roleSetter(user, InterviewerRowRole.CODE, RoleAssignmentRoleType.ROW_LEVEL);
                break;
            case "JUN_HR":
                roleSetter(user, JunHRresRole.CODE, RoleAssignmentRoleType.RESOURCE);
                roleSetter(user, JunHRRowRole.CODE, RoleAssignmentRoleType.ROW_LEVEL);
                break;
            case "HR":
                roleSetter(user, HRresRole.CODE, RoleAssignmentRoleType.RESOURCE);
                break;
            case "ADMIN":
                roleSetter(user, FullAccessRole.CODE, RoleAssignmentRoleType.RESOURCE);
                break;
        }
    }

    public void roleSetter(String userName, String roleCode, String roleType) {
        RoleAssignmentEntity assignment = dataManager.create(RoleAssignmentEntity.class);
        assignment.setUsername(userName);
        assignment.setRoleCode(roleCode);
        assignment.setRoleType(roleType);
        dataManager.save(assignment);
    }

    public void deleteAllUserRoles(String user) {
        dataManager.load(RoleAssignmentEntity.class)
                .query("e.username = ?1", user)
                .list()
                .forEach(roleAssignment ->
                        dataManager.remove(roleAssignment));
    }

    public boolean isAdmin(String userName) {
        User user = dataManager.load(User.class)
                .query("e.username = ?1", userName)
                .one();
        return user.getRole() == Role.ADMIN;
    }

}