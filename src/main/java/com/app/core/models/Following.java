package com.app.core.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "Following")
@AllArgsConstructor
@NoArgsConstructor
public class Following implements Serializable {
    @Id private String id;
    @JoinColumn private String userA;
    @JoinColumn private String userB;
    private LocalDateTime followedOn;
}
