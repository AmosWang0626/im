package com.amos.comm.base;

import lombok.Data;

import java.math.BigDecimal;

/**
 * PROJECT: comm
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2018/10/29
 */
@Data
public class OrderEntity {

    private String orderId;

    private String type;

    private String status;

    private BigDecimal payment;

    private String remark;

}
