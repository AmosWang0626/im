package com.amos.im.controller.request;

import com.amos.im.common.BasePacket;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author Daoyuan
 * @date 2019/3/20
 */
public class CommandFactory {

    /**
     * 根据命令获取类
     *
     * @param command 命令
     * @return class
     */
    public static Class<? extends BasePacket> getRequestType(byte command) {
        // 根据包名,拿到包下的指定的class
        String packageName = Command.class.getPackage().getName();
        List<Class> classByPackageName;
        try {
            classByPackageName = getClassByPackageName(packageName, "Command");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (classByPackageName == null) {
            return null;
        }

        AtomicReference<Class<? extends BasePacket>> clazz = new AtomicReference<>();
        classByPackageName.forEach(tempClass -> {
            try {
                BasePacket instance = (BasePacket) tempClass.newInstance();
                if (instance.getCommand().equals(command)) {
                    clazz.set(tempClass);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return clazz.get();
    }

    /**
     * 根据包名, 拿到包下筛选后的类
     *
     * @param packageName 包名
     * @param filter      筛选条件
     */
    private static List<Class> getClassByPackageName(String packageName, String filter) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        URL resource = contextClassLoader.getResource(path);
        if (resource == null) {
            return null;
        }

        File[] files = new File(resource.getPath()).listFiles();
        if (files == null) {
            return null;
        }

        List<Class> classes = new ArrayList<>();

        for (File childFile : files) {
            if (!childFile.isFile()) {
                continue;
            }
            // 筛除Command工具类
            if (childFile.getName().contains(filter)) {
                continue;
            }
            String classPath = packageName + "." + childFile.getName().replace(".class", "");

            classes.add(contextClassLoader.loadClass(classPath));
        }

        if (classes.size() == 0) {
            return null;
        }

        return classes;
    }

}
