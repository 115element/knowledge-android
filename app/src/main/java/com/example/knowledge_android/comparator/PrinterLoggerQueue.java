package com.example.knowledge_android.comparator;

/**
 * A logging buffer for printer logging service.
 *
 * @author Bruce You
 */
public class PrinterLoggerQueue {

    private StringBuilder buffer = new StringBuilder();

    synchronized public String retrieve() {
        if (buffer.length() == 0) {
            return "";
        } else {
            String content = buffer.toString();
            buffer.setLength(0);
            return content;
        }
    }

    synchronized public void add(String line) {
        if (line == null || line.length() == 0)
            return;

        // shrink it while nobody watch it
        if (buffer.length() > 2048)
            buffer.delete(0, 1024);

        String log = line.replaceAll("\\x1B", "[ESC]").replaceAll("\\n\\r", "\n");
        log = log.replaceAll("\\x1D", "[GS]");
        buffer.append(log);
        if (!log.endsWith("\n"))
            buffer.append('\n');
    }
}
