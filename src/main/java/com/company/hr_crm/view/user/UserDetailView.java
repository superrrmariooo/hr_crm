package com.company.hr_crm.view.user;

import com.company.hr_crm.app.RoleProcessing;
import com.company.hr_crm.app.UserService;
import com.company.hr_crm.entity.Role;
import com.company.hr_crm.entity.User;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.sys.LogoutSupport;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Objects;
import java.util.Set;


@Route(value = "users/:id", layout = MainView.class)
@ViewController("User.detail")
@ViewDescriptor("user-detail-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailView extends StandardDetailView<User> {

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private PasswordField passwordField;
    @ViewComponent
    private PasswordField confirmPasswordField;
    @Autowired
    private EntityStates entityStates;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private JmixSelect<Object> roleField;
    @Autowired
    protected Dialogs dialogs;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private InstanceContainer<User> userDc;
    @Autowired
    private RoleProcessing roleProcessing;
    @Autowired
    private LogoutSupport logoutSupport;
    @Autowired
    private UserService userService;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<User> event) {
        usernameField.setReadOnly(false);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        } else {
            if (userDc.getItem().getUsername().equalsIgnoreCase(currentAuthentication.getUser().getUsername())) {
                roleField.setReadOnly(true);
            }
        }
    }

    @Subscribe
    public void onValidation(final ValidationEvent event) {
        if (entityStates.isNew(getEditedEntity())
                && !Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
            event.getErrors().add(messageBundle.getMessage("passwordsDoNotMatch"));
        }
    }

    @Subscribe
    protected void onBeforeSave(final BeforeSaveEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }

    @Subscribe("roleField")
    public void onRoleFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<?>, Role> event) {
        if (Objects.nonNull(event.getValue())
                &&(Objects.nonNull(event.getOldValue()))
                &&((Objects.equals(event.getValue().toString(), "ADMIN")))) {
            oneAdminCheck(event.getOldValue());
        }
    }

    public void oneAdminCheck(Role role) {
        Html htmlContent = new Html(messageBundle.getMessage("htmlNewAdmin"));
        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("setAdmin"))
                .withContent(htmlContent)
                .withActions(
                        new DialogAction(DialogAction.Type.OK)
                                .withText(messageBundle.getMessage("set")),
                        new DialogAction(DialogAction.Type.CLOSE)
                                .withText(messageBundle.getMessage("cancel"))
                                .withHandler(e ->{
                                    roleField.setValue(role);
                                })
                )
                .open();
    }

    @Subscribe
    public void onAfterSave(final AfterSaveEvent event) {
        userService.roleProcessingAfterSave(usernameField.getValue(), roleField.getValue().toString());
    }


    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        return userService.saveUser(saveContext);
    }

}