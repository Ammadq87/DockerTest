package com.app.core.models;

import com.app.core.models.follow.FollowPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    private User user;
    private List<FollowPair> following;
    private List<FollowPair> followers;
}
