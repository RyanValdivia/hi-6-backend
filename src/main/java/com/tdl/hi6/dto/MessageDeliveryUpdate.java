package com.tdl.hi6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDeliveryUpdate {
    private String content;
    private MessageDeliveryStatus status;
}
