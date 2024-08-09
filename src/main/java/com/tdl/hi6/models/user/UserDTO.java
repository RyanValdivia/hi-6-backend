package com.tdl.hi6.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String names;
    private String surnames;
    private String description;
    private String imageURL;
}
