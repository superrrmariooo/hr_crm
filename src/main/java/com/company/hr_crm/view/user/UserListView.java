package com.company.hr_crm.view.user;

import com.company.hr_crm.app.UserService;
import com.company.hr_crm.entity.Role;
import com.company.hr_crm.entity.User;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import java.util.List;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private JmixButton createBtn;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent
    private JmixButton changePassword;
    @ViewComponent
    private JmixButton removeBtn;
    @ViewComponent
    private GenericFilter genericFilter;
    @Autowired
    private UserService userService;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        User user = dataManager.load(User.class)
                .query("e.username = ?1",
                        currentAuthentication.getUser().getUsername())
                .one();
        if (user.getRole() != Role.ADMIN){
            createBtn.setVisible(false);
            editBtn.setVisible(false);
            changePassword.setVisible(false);
            removeBtn.setVisible(false);
            genericFilter.setVisible(false);
        }
    }

    @Install(to = "usersDataGrid.remove", subject = "delegate")
    private void usersDataGridRemoveDelegate(final Collection<User> collection) {
        userService.deleteUsers(collection);
    }

    @Install(to = "usersDl", target = Target.DATA_LOADER)
    private List<User> usersDlLoadDelegate(final LoadContext<User> loadContext) {
        return userService.getUsers(loadContext);
    }

}