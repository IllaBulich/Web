package com.example.web.models;

import java.time.LocalDate;

public class RentalRequest {

    private Long userId;
    private Long immovableId;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getImmovableId() {
        return immovableId;
    }

    public void setImmovableId(Long immovableId) {
        this.immovableId = immovableId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
