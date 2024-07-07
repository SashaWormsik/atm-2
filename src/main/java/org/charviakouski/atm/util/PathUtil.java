package org.charviakouski.atm.util;

import java.io.File;
import java.io.FileNotFoundException;

public final class PathUtil {

    private PathUtil() {
    }

    public static boolean checkFilePath(String fileName) throws FileNotFoundException {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new FileNotFoundException("FileName is null or empty string");
        }
        File file = new File(fileName);
        return file.exists();
    }
}
