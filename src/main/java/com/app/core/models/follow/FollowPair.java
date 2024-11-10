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
    @EmbeddedId private FollowPairID followPairID;
    private LocalDateTime followedOn;
}
