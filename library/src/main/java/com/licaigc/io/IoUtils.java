package com.licaigc.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by walfud on 2015/11/19.
 */
public class IoUtils {

    public static final String TAG = "IoUtils";

    // Input
    public static byte[] input(File file) {
        byte[] bytes = new byte[0];

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }
    // Output

    /**
     * @param file
     * @param bytes
     * @return null if fail
     */
    public static File output(File file, byte[] bytes) {
        return output(file, bytes, false);
    }

    public static File output(File file, byte[] bytes, boolean append) {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file, append));
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return file;
    }

    // Read
    public static String read(File file) {
        return new String(input(file));
    }

    // Write
    public static File write(File file, String data) {
        return write(file, data, "utf-8");
    }

    public static File write(File file, String data, String encoding) {
        try {
            return output(file, data.getBytes(encoding));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Delete recursively
     *
     * @param target
     * @return the count of file deleted
     */
    public static int delete(File target) {
        return foreachFileAndDir(target, new ForeachFileHandler() {
            @Override
            public boolean handle(File file) {
                file.delete();
                return true;
            }
        }, true);
    }

    /**
     * Only file will trigger `handler`
     *
     * @param target
     * @param handler
     * @return
     * @see #foreachFileAndDir(File, ForeachFileHandler, boolean)
     */
    public static int foreachFile(File target, ForeachFileHandler handler) {
        return foreachFileAndDir(target, handler, false);
    }

    /**
     * Travel all sub-target in depth.
     *
     * @param target
     * @param includeDir `true`: directory will also trigger the `handler`, `false`: only file trigger `handler`
     * @return the count of file which is handled
     * @see com.licaigc.io.IoUtils.ForeachFileHandler
     */
    public static int foreachFileAndDir(File target, ForeachFileHandler handler, boolean includeDir) {
        int count = 0;

        if (false) {
            // Stub
        } else if (target.isFile()) {
            count += handler.handle(target) ? 1 : 0;
        } else if (target.isDirectory()) {
            File[] subTargets = target.listFiles();
            if (subTargets != null) {
                for (File subTarget : subTargets) {
                    count += foreachFileAndDir(subTarget, handler, includeDir);
                }
            }

            if (includeDir) {
                handler.handle(target);
            }
        } else {
            // Do nothing
        }

        return count;
    }

    //
    public interface ForeachFileHandler {
        /**
         * @param file
         * @return true: handled, false: ignore
         */
        boolean handle(File file);
    }
}
