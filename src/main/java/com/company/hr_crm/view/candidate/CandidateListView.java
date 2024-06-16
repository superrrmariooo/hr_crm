package com.company.hr_crm.view.candidate;

import com.company.hr_crm.app.CandidateService;
import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.LoadContext;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Route(value = "candidates", layout = MainView.class)
@ViewController("Candidate.list")
@ViewDescriptor("candidate-list-view.xml")
@LookupComponent("candidatesDataGrid")
@DialogMode(width = "64em")
public class CandidateListView extends StandardListView<Candidate> {

    @Autowired
    private Messages messages;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private CandidateService candidateService;

    @Supply(to = "candidatesDataGrid.photo", subject = "renderer")
    private Renderer<Candidate> candidatesDataGridPhotoRenderer() {
        return new ComponentRenderer<>(candidate -> {
            FileRef fileRef = candidate.getPhoto();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                image.setClassName("candidate-photo");

                return image;
            } else {
                return null;
            }
        });
    }

    @Supply(to = "candidatesDataGrid.candidateStatus", subject = "renderer")
    private Renderer<Candidate> candidatesDataGridCandidateStatusRenderer() {
        return new ComponentRenderer<>(this::createGradeComponent, this::gradeComponentUpdater);
    }

    protected Span createGradeComponent() {
        Span span = uiComponents.create(Span.class);
        span.getElement().getThemeList().add("badge");
        return span;
    }

    protected void gradeComponentUpdater(Span span, Candidate candidate) {
        if (candidate.getCandidateStatus() != null) {
            span.setText(messages.getMessage(candidate.getCandidateStatus()));
            switch (candidate.getCandidateStatus()) {
                case REVIEW, INTERVIEW -> span.getElement().getThemeList().add("contrast");
                case ACCEPTED -> span.getElement().getThemeList().add("success");
                case DENIED -> span.getElement().getThemeList().add("error");
            }
        }
    }

    @Install(to = "candidatesDataGrid.remove", subject = "delegate")
    private void candidatesDataGridRemoveDelegate(final Collection<Candidate> collection) {
        candidateService.deleteCandidates(collection);
    }

    @Install(to = "candidatesDl", target = Target.DATA_LOADER)
    private List<Candidate> candidatesDlLoadDelegate(final LoadContext<Candidate> loadContext) {
        return candidateService.getCandidates(loadContext);
    }

}