package com.amos.im.core.command;

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
            classByPackageName = getClassByPackageName(packageName);
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
     * 根据包名, 拿到request/response包下指定类
     *
     * @param packageName 包名
     */
    private static List<Class> getClassByPackageName(String packageName) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        URL requestUrl = contextClassLoader.getResource(path + "/request");
        URL responseUrl = contextClassLoader.getResource(path + "/response");

        List<Class> classes = new ArrayList<>();

        // request 包下所有类
        File[] requestFiles = null;
        if (requestUrl != null) {
            requestFiles = new File(requestUrl.getPath()).listFiles();
        }
        if (requestFiles != null) {
            for (File childFile : requestFiles) {
                if (!childFile.isFile()) {
                    continue;
                }
                String classPath = packageName + ".request." + childFile.getName().replace(".class", "");
                classes.add(contextClassLoader.loadClass(classPath));
            }
        }

        // response 包下所有类
        File[] responseFiles = null;
        if (responseUrl != null) {
            responseFiles = new File(responseUrl.getPath()).listFiles();
        }
        if (responseFiles != null) {
            for (File childFile : responseFiles) {
                if (!childFile.isFile()) {
                    continue;
                }
                String classPath = packageName + ".response." + childFile.getName().replace(".class", "");
                classes.add(contextClassLoader.loadClass(classPath));
            }
        }

        if (classes.size() == 0) {
            return null;
        }

        return classes;
    }

}
