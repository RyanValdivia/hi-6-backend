package com.tdl.hi6.dto;

import com.tdl.hi6.models.friendrequest.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SentFriendRequest {
    private Status status;
    private UserDTO receiver;
}
