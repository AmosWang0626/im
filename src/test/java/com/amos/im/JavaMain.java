package com.amos.im;

import com.amos.im.core.request.LoginRequest;
import com.amos.im.core.response.LoginResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/1/18
 */
public class JavaMain {

    public static void main(String[] args) {
        System.out.println("(1 << 4) = " + (1 << 4));
        System.out.println("(1 << 4 >> 1) = " + (1 << 4 >> 1));
        System.out.println("=================================================");

        // 手机号脱敏
        System.out.println("18937128861".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        System.out.println("=================================================");

        // public native boolean isInstance(Object obj);
        System.out.println(LoginRequest.class.isInstance(new LoginRequest()));
        System.out.println(LoginResponse.class.isInstance(new LoginRequest()));
        System.out.println("=================================================");

        // test Map putIfAbsent
        Map<Integer, String> map = new HashMap<>();
        map.put(777, "hello world!");
        System.out.println("map.put(777, " + map.get(777) + ")");
        String lal = map.putIfAbsent(777, "lal");
        System.out.println("map.putIfAbsent(777, \"lal\") >>>>>>> " + lal);
        lal = map.putIfAbsent(666, "lal");
        System.out.println("map.putIfAbsent(666, \"lal\") >>>>>>> " + lal);
    }

    private static void testByteBuf() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        print("getByte()", buffer);

        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()" + (buffer.readableBytes() + 1), buffer);

        // read 方法改变读指针
        // byte[] dst = new byte[buffer.readableBytes()];
        byte[] dst = new byte[4];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println("ByteBufUtil.hexDump(buffer): " + ByteBufUtil.hexDump(buffer));
        System.out.println();
    }

    private static void lala() {
        System.out.println("(1 << 4) = " + (1 << 4));

        System.out.println("(1 & 1) = " + (1 & 1));
        System.out.println("(1 & 2) = " + (1 & 2));
        System.out.println("(1 & 3) = " + (1 & 3));
        System.out.println("(2 & 2) = " + (2 & 2));
        System.out.println("(2 & 6) = " + (2 & 6));
        System.out.println("(2 & 7) = " + (2 & 7));
    }

}
