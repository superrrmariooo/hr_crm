package com.company.hr_crm.app;

import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.entity.Comment;
import com.company.hr_crm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
public class CommentRepository {

    @Autowired
    private DataManager dataManager;

    public void deleteComments(Collection<Comment> collection) {
        for (Comment comment: collection) {
            dataManager.remove(comment);
        }
    }

    public void createComment(String text, Candidate candidate, User user) {
        Comment comment = dataManager.create(Comment.class);
        comment.setText(text);
        comment.setDataTime(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setCandidate(candidate);
        dataManager.save(comment);
    }

    public List<Comment> getComments(LoadContext<Comment> loadContext) {
        return dataManager.loadList(loadContext);
    }
}