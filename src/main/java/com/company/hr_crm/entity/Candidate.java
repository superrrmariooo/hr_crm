package com.company.hr_crm.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "CANDIDATE", indexes = {
        @Index(name = "IDX_CANDIDATE_VACANCY", columnList = "VACANCY_ID")
})
@Entity
public class Candidate {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @Column(name = "PHOTO", length = 1024)
    private FileRef photo;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "SURNAME", nullable = false)
    @NotNull
    private String surname;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "PHONE", nullable = false)
    @NotNull
    private String phone;

    @Column(name = "EMAIL", nullable = false)
    @NotNull
    private String email;

    @Column(name = "AGE", nullable = false)
    @NotNull
    private Integer age;

    @Column(name = "EXPERIENCE", nullable = false)
    @NotNull
    private Integer experience;

    @Column(name = "SALARY", nullable = false)
    @NotNull
    private Integer salary;

    @NotNull
    @JoinColumn(name = "VACANCY_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Vacancy vacancy;

    @Column(name = "STATUS")
    private String candidateStatus;

    public FileRef getPhoto() {
        return photo;
    }

    public void setPhoto(FileRef photo) {
        this.photo = photo;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public CandidateStatus getCandidateStatus() {
        return candidateStatus == null ? null : CandidateStatus.fromId(candidateStatus);
    }

    public void setCandidateStatus(CandidateStatus candidateStatus) {
        this.candidateStatus = candidateStatus == null ? null : candidateStatus.getId();
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
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