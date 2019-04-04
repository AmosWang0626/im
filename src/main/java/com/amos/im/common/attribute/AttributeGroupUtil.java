package com.amos.im.common.attribute;

import com.amos.im.controller.dto.GroupInfoVO;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class AttributeGroupUtil {

    /*
     * ======================
     * ===== 群聊相关 *******
     * ======================
     */

    /**
     * 客户端 --- 加入群组
     */
    public static void addGroupClient(Channel channel, String groupId, String groupName) {
        Map<String, GroupInfoVO> map = channel.attr(AttributeConstant.GROUP_INFO_MAP).get();
        if (map == null) {
            map = new HashMap<>(4);
        }
        map.put(groupId, new GroupInfoVO().setGroupId(groupId).setGroupName(groupName));
        channel.attr(AttributeConstant.GROUP_INFO_MAP).set(map);
    }

    /**
     * 客户端 --- 退出群组
     */
    public static void quitGroupClient(Channel channel, String groupId) {
        Map<String, GroupInfoVO> groupInfoVOMap = channel.attr(AttributeConstant.GROUP_INFO_MAP).get();

        if (groupInfoVOMap.get(groupId) != null) {
            groupInfoVOMap.remove(groupId);
            channel.attr(AttributeConstant.GROUP_INFO_MAP).set(groupInfoVOMap);
        }
    }

    /**
     * 客户端 --- 获取指定群信息
     */
    public static GroupInfoVO getGroupInfoClient(Channel channel, String groupId) {
        return channel.attr(AttributeConstant.GROUP_INFO_MAP).get().get(groupId);
    }

    /**
     * 客户端 --- 获取已加入群的群信息
     */
    public static List<GroupInfoVO> getGroupInfoClient(Channel channel) {
        Map<String, GroupInfoVO> groupInfoVOMap = channel.attr(AttributeConstant.GROUP_INFO_MAP).get();
        if (groupInfoVOMap == null || groupInfoVOMap.size() == 0) {
            return null;
        }
        return new ArrayList<>(groupInfoVOMap.values());
    }

    /*
     * 服务端
     */

    /**
     * GroupId >>> ChannelGroup
     */
    private static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();
    private static final Map<String, GroupInfoVO> GROUP_INFO = new ConcurrentHashMap<>();

    /**
     * 创建群组
     */
    public static void createGroupServer(ChannelGroup channels, GroupInfoVO groupInfoVO) {
        GROUP_INFO.put(groupInfoVO.getGroupId(), groupInfoVO);
        CHANNEL_GROUP_MAP.put(groupInfoVO.getGroupId(), channels);
    }

    /**
     * 增加群成员/删除群成员
     */
    public static void updateGroupServer(String groupId, ChannelGroup channels) {
        CHANNEL_GROUP_MAP.put(groupId, channels);
    }

    /**
     * 删除群组
     */
    public static void removeGroupServer(String groupId) {
        GROUP_INFO.remove(groupId);
        CHANNEL_GROUP_MAP.remove(groupId);
    }

    /**
     * 根据群号获取群组
     */
    public static ChannelGroup getChannelGroup(String groupId) {
        return CHANNEL_GROUP_MAP.get(groupId);
    }

    /**
     * 获取群信息
     */
    public static GroupInfoVO getGroupInfoServer(String groupId) {
        return GROUP_INFO.get(groupId);
    }
}
