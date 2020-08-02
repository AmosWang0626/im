package com.amos.im.common.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/21
 */
public class IdUtil {

    private final AtomicLong groupId = new AtomicLong(0L);

    private static volatile IdUtil idUtil;

    public static IdUtil getInstance() {
        if (idUtil == null) {
            idUtil = new IdUtil();
        }
        return idUtil;
    }

    public String getGroupId() {
        return "g0" + groupId.getAndAdd(1L);
    }

    public String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
