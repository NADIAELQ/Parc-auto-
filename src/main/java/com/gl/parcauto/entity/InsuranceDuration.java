package com.gl.parcauto.entity;

public enum InsuranceDuration {
    MONTH(1), THREE_MONTH(3), SIX_MONTH(6), YEAR(12);
    private final Integer numberOfMonth;
    InsuranceDuration(Integer numberOfMonth) {
        this.numberOfMonth = numberOfMonth;
    }
    public Integer getNumberOfMonth() {
        return numberOfMonth;
    }
}
