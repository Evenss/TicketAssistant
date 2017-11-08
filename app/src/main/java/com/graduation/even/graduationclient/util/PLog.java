package com.graduation.even.graduationclient.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class PLog {
	private static final String TAG = "Graduation";
	
	private static final boolean isWriteLog2File = false;
 
	private static boolean bEnableLog = true;
	
    public static String getRunInfo(){
		StringBuffer toStringBuffer = null;
		try {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[3];
			StackTraceElement traceElement2 = ((new Exception())
					.getStackTrace())[4];

			toStringBuffer = new StringBuffer(""
					+ Thread.currentThread().getId()).append(" | ")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement2.getMethodName()).append(" -> ")
					.append(traceElement.getMethodName()).append("()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toStringBuffer != null ? toStringBuffer.toString() : null;
	}

	public static String wrapLog(String msg) {
		return "[" + getRunInfo() + "] " + msg;
	}

	public static void i(String msg) {
		if (false == bEnableLog) {
			return;
		}
		Log.i(TAG, wrapLog(msg));
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg));
		}
	}

	public static void e(String msg) {
		if (false == bEnableLog) {
			return;
		}
		Log.e(TAG, wrapLog(msg));
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg));
		}
	}

	public static void e(String msg, Throwable tr) {
		if (false == bEnableLog) {
			return;
		}
		Log.e(TAG, wrapLog(msg), tr);
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg) + ": " + getThrowableStr(tr));
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (false == bEnableLog) {
			return;
		}
		Log.e(TAG, wrapLog(msg), tr);
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg) + ": " + getThrowableStr(tr));
		}
	}

	public static void w(String msg) {
		if (false == bEnableLog) {
			return;
		}
		Log.w(TAG, wrapLog(msg));
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg));
		}
	}

	public static void w(String msg, Throwable tr) {
		if (false == bEnableLog) {
			return;
		}
		Log.w(TAG, wrapLog(msg), tr);
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg) + ": " + getThrowableStr(tr));
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (false == bEnableLog) {
			return;
		}
		Log.w(TAG, wrapLog(msg), tr);
		if (isWriteLog2File) {
			writeLog2File(wrapLog(msg) + ": " + getThrowableStr(tr));
		}
	}

	private static void writeLog2File(String log) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return;
		}
		String path = Environment.getExternalStorageDirectory() + "/NewSDK.log";
		File file = new File(path);
		FileOutputStream fos = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
		try {
			fos = new FileOutputStream(file, true);
			fos.write((sdf.format(date) + " " + log + '\n').getBytes());
		} catch (Exception e) {
			Log.w(TAG, "writeLog2File日志写入错误");
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static String getThrowableStr(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		return writer.toString();
	}

}