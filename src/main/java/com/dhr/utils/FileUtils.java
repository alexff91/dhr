package com.dhr.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static String findFileName(final String dir, final String baseName, final String extension) {
        String name = String.format("%s.%s", baseName, extension);
        Path ret = Paths.get(dir, name);
        if (!Files.exists(ret))
            return name;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            name = String.format("%s%d.%s", baseName, i, extension);
            ret = Paths.get(dir, name);
            if (!Files.exists(ret))
                return name;
        }
        throw new IllegalStateException("Fail finding file name");
    }

    public static String getFileName(String file) {
        String extension = "";
        int i = file.lastIndexOf('.');
        if (i > 0) {
            extension = file.substring(0, i);
        }
        return extension;
    }

    public static String getFileExtension(String file) {
        String extension = "";
        int i = file.lastIndexOf('.');
        if (i > 0) {
            extension = file.substring(i + 1);
        }
        return extension;
    }
}
