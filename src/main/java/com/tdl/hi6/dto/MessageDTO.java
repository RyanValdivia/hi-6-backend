package com.tdl.hi6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String content;
    private String senderEmail;
    private String receiverEmail;
    private Date timestamp;
}
