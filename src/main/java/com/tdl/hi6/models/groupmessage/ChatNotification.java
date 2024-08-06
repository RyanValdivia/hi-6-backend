package com.tdl.hi6.models.groupmessage;

import com.tdl.hi6.models.groupmessage.enums.Cause;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatNotification {
    private String content;
    private UUID chatRoomId;
    private Cause cause;
}
