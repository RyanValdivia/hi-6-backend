package com.tdl.hi6.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
