package com.company.hr_crm.app;

import com.company.hr_crm.entity.Candidate;
import com.company.hr_crm.entity.CandidateStatus;
import com.company.hr_crm.entity.Comment;
import com.company.hr_crm.entity.User;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class CandidateService {

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> getCandidates(LoadContext<Candidate> loadContext) {
        return candidateRepository.getCandidates(loadContext);
    }

    public void deleteCandidates(Collection<Candidate> collection) {
        candidateRepository.deleteCandidates(collection);
    }

    public Set<Object> saveCandidate(SaveContext saveContext) {
        return candidateRepository.saveCandidate(saveContext);
    }

    public void deleteComments(Collection<Comment> collection) {
        commentRepository.deleteComments(collection);
    }

    public void createComment(String text, Candidate candidate) {
        commentRepository.createComment(text, candidate, (User) currentAuthentication.getUser());
    }

    public void setReviewStatusOnInit(Candidate candidate) {
        candidate.setCandidateStatus(CandidateStatus.REVIEW);
    }

    public List<Comment> getComments(LoadContext<Comment> loadContext) {
        return commentRepository.getComments(loadContext);
    }
}
