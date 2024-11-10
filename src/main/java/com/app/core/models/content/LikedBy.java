package com.app.core.models.content;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LikedBy")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikedBy {
    @Id private int id;
    @JoinColumn private String username;
    @JoinColumn private String contentId;
    private LocalDateTime likedOn;
}
