package com.amos.im.common.attribute;

import com.amos.im.controller.dto.GroupVO;
import com.amos.im.controller.dto.LoginVO;
import io.netty.util.AttributeKey;

import java.util.Map;

/**
 * PROJECT: im
 * DESCRIPTION: 客户端Channel里存储数据常量
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public interface AttributeConstant {

    /**
     * 客户端留存登录信息
     */
    AttributeKey<LoginVO> LOGIN_INFO = AttributeKey.newInstance("login");
    /**
     * 客户端留存群组信息
     */
    AttributeKey<Map<String, GroupVO>> GROUP_INFO_MAP = AttributeKey.newInstance("joinedGroups");

}
