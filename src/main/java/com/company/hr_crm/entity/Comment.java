package com.company.hr_crm.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "COMMENT_", indexes = {
        @Index(name = "IDX_COMMENT__AUTHOR", columnList = "AUTHOR_ID"),
        @Index(name = "IDX_COMMENT__CANDIDATE", columnList = "CANDIDATE_ID")
})
@Entity(name = "Comment_")
public class Comment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @Column(name = "TEXT", nullable = false)
    @NotNull
    private String text;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User author;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Candidate candidate;

    @Column(name = "DATA_TIME")
    private LocalDateTime dataTime;

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}