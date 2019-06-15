package com.amos.im.core.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/21
 */
@Data
@Accessors(chain = true)
public class LoginInfoVO {

    private String token;

    private String username;

}
