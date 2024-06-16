package com.company.hr_crm.view.comment;

import com.company.hr_crm.entity.Comment;
import com.company.hr_crm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "comments", layout = MainView.class)
@ViewController("Comment_.list")
@ViewDescriptor("comment-list-view.xml")
@LookupComponent("commentsDataGrid")
@DialogMode(width = "64em")
public class CommentListView extends StandardListView<Comment> {
}