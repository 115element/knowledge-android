package com.example.knowledge_android.comparator;



import java.io.*;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author alan
 */
public class ProcessExec {
    static Logger log;// = LoggerFactory.getLogger(ProcessExec.class);

    public static int exec(File directory, String... args) throws IOException, InterruptedException {
        //log.info("exec [{}]", String.join(",", Arrays.asList(args)));
        ProcessBuilder pb = new ProcessBuilder(args);
        if (directory != null) {
            pb.directory(directory);
        }
        Process process = pb.start();
        // any error message?
        StreamGobbler errorGobbler = new
                StreamGobbler(process.getErrorStream(), "ERROR");

        // any output?
        StreamGobbler outputGobbler = new
                StreamGobbler(process.getInputStream(), "OUTPUT");

        // kick them off
        errorGobbler.start();
        outputGobbler.start();

        int exitVal = process.waitFor();
        return exitVal;
    }

    public static int exec(String... args) throws IOException, InterruptedException {
        return exec(null, args);
    }
}

class StreamGobbler extends Thread {
    //static Logger log = LoggerFactory.getLogger(StreamGobbler.class);

    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                //log.debug(type + ">" + line);
            }
        } catch (IOException e) {
            //log.error("", e);
        }
    }
}
