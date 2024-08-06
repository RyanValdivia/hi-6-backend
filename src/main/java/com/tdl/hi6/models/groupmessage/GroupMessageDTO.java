package com.tdl.hi6.models.groupmessage;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class GroupMessageDTO {
    private String content;
    private UUID senderId;
    private UUID chatRoomId;
    private Date timestamp;
}
