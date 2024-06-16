package com.company.hr_crm.app;

import com.company.hr_crm.entity.Role;
import com.company.hr_crm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class UserRepository {

    @Autowired
    private DataManager dataManager;

    public List<User> getUsers(LoadContext<User> loadContext) {
        return dataManager.loadList(loadContext);
    }

    public void deleteUsers(Collection<User> collection) {
        for (User user : collection) {
            dataManager.remove(user);
        }
    }

    public Set<Object> saveUser(SaveContext saveContext) {
        return dataManager.save(saveContext);
    }

    public User getUserByUserName(String username) {
        return dataManager.load(User.class)
                .query("e.username = ?1", username)
                .one();
    }

    public void setHRRoleToUser(String username) {
        User user = getUserByUserName(username);
        user.setRole(Role.HR);
        dataManager.save(user);
    }
}