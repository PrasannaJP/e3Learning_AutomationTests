package com.webuiframework.oua.uitests.utils;

import java.io.File;

public final class MakeDir {

    private MakeDir(){}
    /**
     * Make directory
     * @param path for directory creation
     */
    public static boolean makeDir(String path) {
        File dir = new File(path);
        return dir.mkdirs();
    }
}

