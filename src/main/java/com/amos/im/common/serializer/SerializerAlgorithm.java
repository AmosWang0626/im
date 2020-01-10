package com.amos.im.common.serializer;

/**
 * PROJECT: im
 * DESCRIPTION: 序列化算法
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public interface SerializerAlgorithm {

    /**
     * JSON 序列化标识
     */
    byte JSON = 1;
    /**
     * HESSIAN 序列化标识
     */
    byte HESSIAN = 2;
    /**
     * PROTOBUF 序列化标识
     */
    byte PROTOBUF = 2;

    /**
     * 默认序列化算法 JsonSerializer
     */
    Serializer DEFAULT = getSerializer((byte) 0);


    /**
     * 根据算法获取序列化工具类
     *
     * @param algorithm 算法
     * @return 序列化工具类
     */
    static Serializer getSerializer(byte algorithm) {
        if (algorithm == HESSIAN) {
            return new HessianSerializer();
        }
        return new JsonSerializer();
    }

}
