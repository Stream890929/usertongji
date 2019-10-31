package com.qfedu.dto;

import lombok.Data;

/**
 * @author Stream
 * @version 1.0
 * @date 2019/10/22 11:58
 */
@Data
public class UserDto {
    private String username;
    private String password;

    public UserDto() {
    }
}
