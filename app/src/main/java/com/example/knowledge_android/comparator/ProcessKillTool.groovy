package com.example.knowledge_android.comparator



/**
 * Created by David on 17-10-11.
 */
class ProcessKillTool {


    private static List<String> read(InputStream inn, String charset) throws IOException {
        List<String> data = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inn, charset));
        String line;
        while((line = reader.readLine()) != null){
            data.add(line);
        }
        reader.close();
        return data;
    };

    public static void kill(List<String> data){
        Set<Integer> pids = new HashSet<Integer>();
        for (String line : data) {
            int offset = line.lastIndexOf(" ");
            String spid = line.substring(offset);
            spid = spid.replaceAll(" ", "");
            int pid = 0;
            try {
                pid = Integer.parseInt(spid);
            } catch (NumberFormatException e) {
                System.out.println("Fetch process error:" + spid);
            }
            //----check process name--------------
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("cmd /c tasklist |findstr " + spid);
            InputStream inputStream = p.getInputStream();
            List<String> readList = read(inputStream, "gbk");
            if (readList.size() == 1) {
                String processInfo = readList.get(0);
                if (processInfo.contains("InlineServer.exe")) {
                    System.out.println("InlineServer process on this port, skip");

                } else {
                    System.out.println("Other process on this port, prepare to kill");
                    pids.add(pid);
                }
            }
            //------------------

        }
        killWithPid(pids);
    }

    public static void killWithPid(Set<Integer> pids){
        for (Integer pid : pids) {
            try {

                Process process = Runtime.getRuntime().exec("taskkill /F /pid "+pid+"");
                InputStream inputStream = process.getInputStream();
                String txt = readTxt(inputStream, "GBK");
                System.out.println(txt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readTxt(InputStream inn,String charset) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inn, charset));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line = reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    };

    public static void main(String[] args) {
        try {
            boolean windows = (System.getProperties().get("os.name").toString().indexOf(
                    "Windows") != -1);
            if (!windows) return;//只在windows系统下判断该进程

            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("cmd /c netstat -aon|findstr 0.0.0.0:3000");
            InputStream inputStream = p.getInputStream();
//        List<String> read = read(inputStream, "UTF-8");
            List<String> read = read(inputStream, "gbk");
            if(read.size() > 0) {
                println('Found port 3000 process');
                kill(read);
                println('Port 3000 process cleared')

            }
        } catch (Exception eex) {
            eex.printStackTrace()
        }

    }

    public static void processKillPid() {
        try {
            boolean windows = (System.getProperties().get("os.name").toString().indexOf(
                    "Windows") != -1);
            if (!windows) return;//只在windows系统下判断该进程

            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("cmd /c netstat -aon|findstr 0.0.0.0:3000");
            InputStream inputStream = p.getInputStream();
//        List<String> read = read(inputStream, "UTF-8");
            List<String> read = read(inputStream, "gbk");
            if (read.size() > 0) {
                kill(read);

            }
        } catch (Exception eex) {
            eex.printStackTrace()
        }
    }
}
