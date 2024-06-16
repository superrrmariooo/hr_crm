package com.company.hr_crm.view.vacancy;

import com.company.hr_crm.app.CandidateService;
import com.company.hr_crm.app.VacancyService;
import com.company.hr_crm.entity.*;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.*;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;

@Route(value = "vacancies/:id", layout = MainView.class)
@ViewController("Vacancy.detail")
@ViewDescriptor("vacancy-detail-view.xml")
@EditedEntityContainer("vacancyDc")
public class VacancyDetailView extends StandardDetailView<Vacancy> {

    @ViewComponent
    private InstanceContainer<Vacancy> vacancyDc;
    @ViewComponent
    private CollectionLoader<Candidate> candidatesDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private Messages messages;
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private CandidateService candidateService;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Vacancy> event) {
        vacancyService.setVacancyStatusAndAuthorOnInit(event);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        final Vacancy vacancy = vacancyDc.getItem();
        candidatesDl.setParameter("vacancy", vacancy);
        candidatesDl.load();
    }

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

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> saveDelegate(final SaveContext saveContext) {
        return vacancyService.saveVacancy(saveContext);
    }

    @Install(to = "candidatesDl", target = Target.DATA_LOADER)
    private List<Candidate> candidatesDlLoadDelegate(final LoadContext<Candidate> loadContext) {
        return candidateService.getCandidates(loadContext);
    }

}