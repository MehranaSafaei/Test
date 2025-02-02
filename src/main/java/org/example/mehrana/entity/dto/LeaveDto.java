package org.example.mehrana.entity.dto;

import java.time.LocalDate;

public class LeaveDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String Description;
    private Long personnelId;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return Description;
    }
    public void setDescription(String reason) {
        this.Description = reason;
    }

    public Long getPersonnelId() {
        return personnelId;
    }
    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }
}
