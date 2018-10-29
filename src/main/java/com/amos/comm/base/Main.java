package com.amos.comm.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PROJECT: comm
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2018/10/29
 */
public class Main {

    private static List<OrderVO> orderVOS = new ArrayList<>();
    private static List<OrderEntity> orderEntities = new ArrayList<>();

    private static void initData() {
        for (int i = 0; i < 10; i++) {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrderId("TRANS_ID_" + i);
            orderVO.setType("VIP");
            orderVO.setPayment(new BigDecimal(i * 1000));
            orderVO.setStatus("SUCCESS");
            orderVO.setDealerSn(System.currentTimeMillis());
            orderVOS.add(orderVO);
        }
        for (int i = 0; i < 8; i++) {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId("TRANS_ID_" + i);
            orderEntity.setType("VIP");
            orderEntity.setPayment(new BigDecimal(i * 1000));
            orderEntity.setStatus("SUCCESS");
            orderEntities.add(orderEntity);
        }
    }

    private static void insertDB() {
        initData();

        // insert to db
        List<OrderEntity> insertEntities = new ArrayList<>();

        Map<String, OrderVO> orderVOMap = new HashMap<>(orderVOS.size());
        orderVOS.forEach(orderVO -> orderVOMap.put(orderVO.getOrderId(), orderVO));

        Map<String, OrderEntity> orderEntityMap = new HashMap<>(orderEntities.size());
        orderEntities.forEach(orderEntity -> orderEntityMap.put(orderEntity.getOrderId(), orderEntity));

        orderVOMap.forEach((orderId, orderVO) -> {
            OrderEntity tempOrderEntity = orderEntityMap.get(orderId);
            if (tempOrderEntity != null) {
                tempOrderEntity.setRemark(orderVO.getDealerSn() + "_" + orderVO.getStatus());
                insertEntities.add(tempOrderEntity);
            }
        });

        System.out.println(insertEntities);
    }

    public static void main(String[] args) {
        insertDB();
    }
}
