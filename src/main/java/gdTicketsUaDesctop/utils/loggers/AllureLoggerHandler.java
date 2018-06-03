package gdTicketsUaDesctop.utils.loggers;

import io.qameta.allure.Allure;

import java.io.*;

import static gdTicketsUaDesctop.utils.loggers.Logger.info;


public class AllureLoggerHandler {

    private static RandomAccessFile raf;
    private static long pointer;
    private static ByteArrayOutputStream buffer;

    public static void initAllureLoggerHandler(String logFilePath) {
        try {
            raf = new RandomAccessFile(logFilePath, "r");
            pointer = raf.getFilePointer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readLogs(long pointer) {
        buffer = new ByteArrayOutputStream();
        try {
            raf.seek(pointer);
            int content;
            while ((content = raf.read()) != -1) {
                buffer.write(content);
            }
            buffer.flush();
            AllureLoggerHandler.pointer = raf.getFilePointer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toByteArray();
    }

    public static void saveLogs(byte[] data) {
        String textData = new String(data);
        Allure.addAttachment("logs", textData);
    }

    public static void attachLogsToStep() {
        byte[] data = readLogs(pointer);
        saveLogs(data);
    }

    public static void closeLogFile() {
        try {
            raf.close();
        } catch (IOException e) {
            info("Couldn't close Allure Log File.");
        }
    }

}