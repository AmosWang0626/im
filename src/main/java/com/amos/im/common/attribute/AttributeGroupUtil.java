package com.amos.im.common.attribute;

import com.amos.im.controller.dto.GroupInfoVO;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
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

    public static boolean hasGroup(Channel channel, String groupId) {
        Map<String, GroupInfoVO> groupVOMap = channel.attr(AttributeConstant.GROUP_INFO_MAP).get();

        return groupVOMap != null && groupVOMap.get(groupId) != null;
    }

    public static void addGroupClient(Channel channel, String groupId, String groupName) {
        Map<String, GroupInfoVO> map = channel.attr(AttributeConstant.GROUP_INFO_MAP).get();
        if (map == null) {
            map = new HashMap<>(4);
        }
        map.put(groupId, new GroupInfoVO().setGroupId(groupId).setGroupName(groupName));
        channel.attr(AttributeConstant.GROUP_INFO_MAP).set(map);
    }

    public static void quitGroup(Channel channel, String groupId) {
        if (hasGroup(channel, groupId)) {
            channel.attr(AttributeConstant.GROUP_INFO_MAP).set(null);
        }
    }

    public static GroupInfoVO getGroupInfo(Channel channel, String groupId) {
        return channel.attr(AttributeConstant.GROUP_INFO_MAP).get().get(groupId);
    }

    /*
     * 服务端
     */

    /**
     * GroupId >>> ChannelGroup
     */
    private static final Map<String, ChannelGroup> CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();
    private static final Map<String, GroupInfoVO> GROUP_INFO = new ConcurrentHashMap<>();

    public static void createGroupServer(ChannelGroup channels, GroupInfoVO groupInfoVO) {
        GROUP_INFO.put(groupInfoVO.getGroupId(), groupInfoVO);
        CHANNEL_GROUP_MAP.put(groupInfoVO.getGroupId(), channels);
    }

    public static void addGroupServer(ChannelGroup channels, String groupId) {
        CHANNEL_GROUP_MAP.put(groupId, channels);
    }

    public static void removeGroup(String groupId) {
        GROUP_INFO.remove(groupId);
        CHANNEL_GROUP_MAP.remove(groupId);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return CHANNEL_GROUP_MAP.get(groupId);
    }

    public static GroupInfoVO getGroupInfo(String groupId) {
        return GROUP_INFO.get(groupId);
    }
}
