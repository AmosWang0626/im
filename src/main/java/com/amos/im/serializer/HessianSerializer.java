package com.amos.im.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * PROJECT: im
 * DESCRIPTION: 序列化---Hessian
 *
 * @author Daoyuan
 * @date 2019/3/19
 */
public class HessianSerializer implements Serializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianSerializer.class);

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.HESSIAN;
    }

    @Override
    public byte[] serialize(Object object) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(os);

        try {
            hessianOutput.writeObject(object);
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Hessian 序列化失败!!! [{}]", e.getMessage());
        }

        return null;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        HessianInput hessianInput = new HessianInput(is);

        try {
            return (T) hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Hessian 反序列化失败!!! [{}]", e.getMessage());
        }

        return null;
    }

}
