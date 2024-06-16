package com.company.hr_crm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "VACANCY", indexes = {
        @Index(name = "IDX_VACANCY_AUTHOR", columnList = "AUTHOR_ID"),
        @Index(name = "IDX_VACANCY_INTERVIEWER", columnList = "INTERVIEWER_ID")
})
@Entity
public class Vacancy {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "DEPARTMENT", nullable = false)
    @NotNull
    private String department;

    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User author;

    @Column(name = "STATUS")
    private String vacancyStatus;

    @JoinColumn(name = "INTERVIEWER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User interviewer;

    public User getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(User interviewer) {
        this.interviewer = interviewer;
    }

    public VacancyStatus getVacancyStatus() {
        return vacancyStatus == null ? null : VacancyStatus.fromId(vacancyStatus);
    }

    public void setVacancyStatus(VacancyStatus vacancyStatus) {
        this.vacancyStatus = vacancyStatus == null ? null : vacancyStatus.getId();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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