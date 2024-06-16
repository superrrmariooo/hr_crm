package com.company.hr_crm.view.comment;

import com.company.hr_crm.entity.Comment;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "comments/:id", layout = MainView.class)
@ViewController("Comment_.detail")
@ViewDescriptor("comment-detail-view.xml")
@EditedEntityContainer("commentDc")
public class CommentDetailView extends StandardDetailView<Comment> {

}