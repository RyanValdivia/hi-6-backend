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
public class ReceivedFriendRequest {
    private Status status;
    private UserDTO sender;
}
