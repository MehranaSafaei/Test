package org.example.mehrana.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class AbstractLeave {

    private Long id;
    private LocalDateTime leaveDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Personnel personnel;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Date", nullable = false)
    public LocalDateTime getLeaveDate() {
        return leaveDate;
    }
    public void setLeaveDate(LocalDateTime leaveDate) {
        this.leaveDate = leaveDate;
    }

    @Column(name = "Start_date")
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "End_date")
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Column(name = "Description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnel_id", nullable = false)
    public Personnel getPersonnel() {
        return personnel;
    }
    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    @Override
    public String toString() {
        return "AbstractLeave{" +
                "id=" + id +
                ", leaveDate=" + leaveDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", personnel=" + personnel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLeave that = (AbstractLeave) o;
        return Objects.equals(id, that.id) && Objects.equals(leaveDate, that.leaveDate) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(description, that.description) && Objects.equals(personnel, that.personnel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, leaveDate, startDate, endDate, description, personnel);
    }
}
