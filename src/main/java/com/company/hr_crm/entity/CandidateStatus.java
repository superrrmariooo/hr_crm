package com.company.hr_crm.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CandidateStatus implements EnumClass<String> {

    REVIEW("REVIEW"),
    INTERVIEW("INTERVIEW"),
    ACCEPTED("ACCEPTED"),
    DENIED("DENIED");

    private final String id;

    CandidateStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CandidateStatus fromId(String id) {
        for (CandidateStatus at : CandidateStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}