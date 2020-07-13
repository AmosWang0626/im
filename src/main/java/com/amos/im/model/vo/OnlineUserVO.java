package com.amos.im.model.vo;

import com.amos.im.common.BasePacket;
import com.amos.im.common.Command;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * DESCRIPTION: 在线用户列表
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/7/11
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
public class OnlineUserVO extends BasePacket {

    private Set<String> tokens;

    @Override
    public Byte getCommand() {
        return Command.ONLINE_USER_RESPONSE;
    }
}
