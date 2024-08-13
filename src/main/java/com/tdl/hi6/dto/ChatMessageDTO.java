package com.tdl.hi6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String content;
    private String senderEmail;
    private String receiverEmail;
    private Date timestamp;
    private ConnectionDTO connection;
    private MessageType type;
    private MessageDeliveryStatus deliveryStatus;
    private List<MessageDeliveryUpdate> messageDeliveryUpdateList;
}
