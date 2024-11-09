package com.app.core.models.follow;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "FollowPair")
@AllArgsConstructor
@NoArgsConstructor
public class FollowPair implements Serializable {
    @Id private String id;
    @JoinColumn private String userA;
    @JoinColumn private String userB;
    private LocalDateTime followedOn;
}
