package com.amos.im.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * DESCRIPTION: 在线用户列表
 *
 * @author amos
 * @date 2020-01-11
 */
@Data
@Accessors(chain = true)
public class UserInfoVO {

    private String token;

    private String username;

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
