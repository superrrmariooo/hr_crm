package com.company.hr_crm.view.vacancy;

import com.company.hr_crm.app.VacancyService;
import com.company.hr_crm.entity.Vacancy;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.core.Messages;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "vacancies", layout = MainView.class)
@ViewController("Vacancy.list")
@ViewDescriptor("vacancy-list-view.xml")
@LookupComponent("vacanciesDataGrid")
@DialogMode(width = "64em")
public class VacancyListView extends StandardListView<Vacancy> {

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Messages messages;
    @Autowired
    private VacancyService vacancyService;

    @Supply(to = "vacanciesDataGrid.vacancyStatus", subject = "renderer")
    private Renderer<Vacancy> vacanciesDataGridVacancyStatusRenderer() {
        return new ComponentRenderer<>(this::createGradeComponent, this::gradeComponentUpdater);
    }

    protected Span createGradeComponent() {
        Span span = uiComponents.create(Span.class);
        span.getElement().getThemeList().add("badge");
        return span;
    }

    protected void gradeComponentUpdater(Span span, Vacancy vacancy) {
        if (vacancy.getVacancyStatus() != null) {
            span.setText(messages.getMessage(vacancy.getVacancyStatus()));

            switch (vacancy.getVacancyStatus()) {
                case ARCHIVE -> span.getElement().getThemeList().add("contrast");
                case ACTIVE -> span.getElement().getThemeList().add("success");
            }
        }
    }

    @Install(to = "vacanciesDl", target = Target.DATA_LOADER)
    private List<Vacancy> vacanciesDlLoadDelegate(final LoadContext<Vacancy> loadContext) {
        return vacancyService.getVacancies(loadContext);
    }

}