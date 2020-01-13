package com.amos.im.core.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/26
 */
@Data
@Accessors(chain = true)
public class GroupInfoVO {

    private String groupId;

    private String groupName;

    private String sponsorName;

    private LocalDateTime createTime;

}
