package com.company.hr_crm.app;

import com.company.hr_crm.entity.Role;
import com.company.hr_crm.entity.User;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.sys.LogoutSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleProcessing roleProcessing;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private LogoutSupport logoutSupport;

    public List<User> getUsers(LoadContext<User> loadContext) {
        return userRepository.getUsers(loadContext);
    }

    public void deleteUsers(Collection<User> collection) {
        userRepository.deleteUsers(collection);
    }

    public Set<Object> saveUser(SaveContext saveContext) {
        return userRepository.saveUser(saveContext);
    }

    public User getUserByUserName(String username) {
        return userRepository.getUserByUserName(username);
    }

    public void roleProcessingAfterSave(String usernameField, String roleField) {
        roleProcessing.setUserRole(usernameField, roleField);
        if (isAdminChanged(usernameField,roleField)) {
            setHRRoleToAdmin();
            logoutSupport.logout();
        }
    }

    private boolean isAdminChanged(String usernameField, String roleField) {
        return Objects.equals(roleField, "ADMIN")
                && !Objects.equals(currentAuthentication.getUser().getUsername(), usernameField);
    }

    private void setHRRoleToAdmin() {
        roleProcessing.setUserRole(currentAuthentication.getUser().getUsername(), "HR");
        userRepository.setHRRoleToUser(currentAuthentication.getUser().getUsername());
    }

}