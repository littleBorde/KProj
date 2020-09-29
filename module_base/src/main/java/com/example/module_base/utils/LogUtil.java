package com.example.module_base.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;

import com.example.module_base.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/7/18--14:03
 * Function:日志工具  可写入文件  方便调式
 */
@SuppressLint("SimpleDateFormat")
public class LogUtil {

    private static String customTagPrefix = "zhdd";

    //日志文件开关  true：输出日志    false：关闭日志
    public static boolean isOutPutLog = BuildConfig.SHOW_LOG;

    // 日志写入文件开关
    public static boolean isWriteToFile = false;

    // 日志文件在sdcard中的路径
    private static String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/yuancore/log/";

    // 日志的输出格式
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 日志文件格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");

    private LogUtil() {
    }

    /**
     * 初始化 日志文件
     */
    public static void init() {
        Logger.init("ivars").methodCount(8);
        File fl = new File(MYLOG_PATH_SDCARD_DIR);
        if (!fl.exists()) {
            IOUtil.makeDirs(fl);
        }
    }

    /**
     * 创建tag 类名加方法名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'd');
    }

    public static void d(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + getExceptionStack(tr), 'd');
    }

    public static void e(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'e');
    }

    public static void e(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + getExceptionStack(tr), 'e');
    }

    public static void i(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'i');
    }

    public static void i(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + getExceptionStack(tr), 'i');
    }

    public static void v(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'v');
    }

    public static void v(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + getExceptionStack(tr), 'v');
    }

    public static void w(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();
        log(tag, content, 'w');
    }

    public static void w(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + getExceptionStack(tr), 'w');
    }

    public static void w(Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, getExceptionStack(tr), 'w');
    }

    /**
     * 日志管理
     *
     * @param tag   tag
     * @param msg   msg
     * @param level level
     */
    private static void log(String tag, String msg, char level) {
        if ('e' == level) {
            Logger.t(tag).e(msg);
        } else if ('w' == level) {
            Logger.t(tag).w(msg);
        } else if ('d' == level) {
            Logger.t(tag).d(msg);
        } else if ('i' == level) {
            Logger.t(tag).i(msg);
        } else {
            Logger.t(tag).v(msg);
        }

        //输出到文件
        if (isWriteToFile) {
            writeLogToFile(String.valueOf(level), tag, msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private synchronized static void writeLogToFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowTime = new Date();
        String needWriteFile = logfile.format(nowTime);
        String needWriteMessage = myLogSdf.format(nowTime) + "    " + mylogtype + "    " + tag + "    " + text;
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFile);
        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String getExceptionStack(Throwable e) {
        if (e == null) {
            return "null";
        }
        ByteArrayOutputStream buf = null;
        PrintWriter printWriter = null;
        try {
            buf = new java.io.ByteArrayOutputStream();
            printWriter = new java.io.PrintWriter(buf, true);
            e.printStackTrace(printWriter);
            return e.getMessage() + " " + buf.toString();
        } catch (Exception e1) {
            return e.getMessage() + " " + e1.getMessage();
        } finally {
            if (buf != null) {
                IOUtil.close(buf);
            }
            if (printWriter != null) {
                IOUtil.close(printWriter);
            }
        }
    }

    /**
     * 删除非本月的日志文件
     */
    public static void delFile() {
        File fl = new File(MYLOG_PATH_SDCARD_DIR);
        Date nowTime = new Date();
        String curMonth = logfile.format(nowTime).substring(0, 7);
        getAllFiles(fl, curMonth);
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    private static void getAllFiles(File root, String curDate) {
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                getAllFiles(f, curDate);
                continue;
            }
            if (!f.getName().substring(0, 7).equalsIgnoreCase(curDate)) {
                f.delete();
            }
        }
    }
}
