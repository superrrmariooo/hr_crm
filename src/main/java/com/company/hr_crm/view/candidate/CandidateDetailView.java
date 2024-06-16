package com.company.hr_crm.view.candidate;

import com.company.hr_crm.app.CandidateService;
import com.company.hr_crm.app.UserService;
import com.company.hr_crm.entity.*;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.app.inputdialog.DialogActions;
import io.jmix.flowui.app.inputdialog.DialogOutcome;
import io.jmix.flowui.app.inputdialog.InputParameter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Route(value = "candidates/:id", layout = MainView.class)
@ViewController("Candidate.detail")
@ViewDescriptor("candidate-detail-view.xml")
@EditedEntityContainer("candidateDc")
public class CandidateDetailView extends StandardDetailView<Candidate> {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private InstanceContainer<Candidate> candidateDc;
    @ViewComponent
    private CollectionLoader<Comment> commentsDl;
    @Autowired
    private Dialogs dialogs;
    @ViewComponent
    private JmixButton editButton;
    @ViewComponent
    private JmixButton deleteButton;
    @ViewComponent
    private DataGrid<Comment> commentsDataGrid2;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private UserService userService;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        final Candidate candidate = candidateDc.getItem();
        commentsDl.setParameter("candidate", candidate);
        commentsDl.load();
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Candidate> event) {
        candidateService.setReviewStatusOnInit(event.getEntity());
    }

    @Subscribe(id = "commentButton", subject = "clickListener")
    public void onCommentButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createInputDialog(this)
                .withHeader(messageBundle.getMessage("addComment"))
                .withLabelsPosition(Dialogs.InputDialogBuilder.LabelsPosition.TOP)
                .withParameters(
                        InputParameter.stringParameter("text").withLabel(messageBundle.getMessage("comment")).withRequired(true)
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String text = closeEvent.getValue("text");
                        candidateService.createComment(text, candidateDc.getItem());
                        commentsDl.load();
                    }
                })
                .open();
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        User user = userService.getUserByUserName(currentAuthentication.getUser().getUsername());
        if (user.getRole()==Role.ADMIN){
            editButton.setVisible(true);
            deleteButton.setVisible(true);
            commentsDataGrid2.setSelectionMode(Grid.SelectionMode.MULTI);
        } else {
            commentsDataGrid2.setSelectionMode(Grid.SelectionMode.NONE);
        }
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        return candidateService.saveCandidate(saveContext);
    }

    @Install(to = "commentsDataGrid2.remove", subject = "delegate")
    private void commentsDataGrid2RemoveDelegate(final Collection<Comment> collection) {
        candidateService.deleteComments(collection);
    }

    @Install(to = "commentsDl", target = Target.DATA_LOADER)
    private List<Comment> commentsDlLoadDelegate(final LoadContext<Comment> loadContext) {
        return candidateService.getComments(loadContext);
    }

} 