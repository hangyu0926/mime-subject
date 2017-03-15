package cn.nanmi.msts.web.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created by tizhou on 2016/12/29.
 */
public class FileUtil {

    public static boolean makeDirs(String filePath) {

        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File folder = new File(filePath);
        return (folder.exists() || folder.mkdirs());
    }

    public static boolean isExistFile(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        }

        File targetFile = new File(path);
        return targetFile.exists();
    }
}
