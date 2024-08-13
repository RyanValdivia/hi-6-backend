package com.tdl.hi6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDTO {
    private String connectionEmail;
    private String convId;
    private boolean isOnline;
}
