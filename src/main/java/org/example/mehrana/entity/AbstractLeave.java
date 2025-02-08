package org.example.mehrana.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import javax.lang.model.element.Name;
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
    private boolean isApproved;
    private boolean isRejected;
    private String rejectReason;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "leave_date", nullable = false)
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

    @Column(name = "is_approved")
    public boolean isApproved() {
        return isApproved;
    }
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Column(name = "is_rejected")
    public boolean isRejected() {
        return isRejected;
    }
    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    @Column(name = "Reject_reason")
    public String getRejectReason() {
        return rejectReason;
    }
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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
                ", approved=" + isApproved +
                ", rejected=" + isRejected +
                ", rejectReason='" + rejectReason + '\'' +
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
