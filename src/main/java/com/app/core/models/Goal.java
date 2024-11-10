package com.app.core.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "Goal")
@AllArgsConstructor
@NoArgsConstructor
public class Goal implements Serializable {
    @Id private String id;
    @JoinColumn private String username;
    private String title;
    private String subtitle;
    private String notes;
    @JoinColumn private String statusId;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private LocalDateTime lastUpdate;
    private Character isChildGoal;
    @JoinColumn private String parentGoalId;
    private LocalDateTime createdOn;
}
