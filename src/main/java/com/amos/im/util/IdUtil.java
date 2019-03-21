package com.amos.im.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/21
 */
public class IdUtil {

    private AtomicLong userId = new AtomicLong(10000L);

    private static volatile IdUtil idUtil;

    public static IdUtil getInstance() {
        if (idUtil == null) {
            idUtil = new IdUtil();
        }
        return idUtil;
    }

    public Long getUserId() {
        return userId.getAndAdd(1L);
    }

    public String getToken() {
        return "TK" + getUserId();
    }

//    public static void main(String[] args) {
//        System.out.println(IdUtil.getInstance().getToToken());
//        System.out.println(IdUtil.getInstance().getToToken());
//    }

}
