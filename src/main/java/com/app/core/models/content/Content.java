package com.app.core.models.content;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Content")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content implements Serializable {
    @Id private String id;
    @JoinColumn private String username;
    private String title;
    private String message;
    private LocalDateTime createdOn;
    private int likeCount;
    private String sharedPostId;
}
