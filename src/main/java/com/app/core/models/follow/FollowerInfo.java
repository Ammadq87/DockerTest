package com.app.core.models.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowerInfo implements Serializable {
    private String followPairId;
    private String name;
    private String username;
    private String email;
    private Date dateOfBirth;
    private LocalDateTime createdOn;
    private LocalDateTime followedOn;
}
