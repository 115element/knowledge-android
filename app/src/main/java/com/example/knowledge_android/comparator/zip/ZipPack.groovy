package com.example.knowledge_android.comparator.zip

import hyi.cream.Version
import org.apache.tools.ant.Project
import org.apache.tools.ant.taskdefs.Zip
import org.apache.tools.ant.types.FileSet

/**
 * zip打包，将程序jar整合成一个server.zip
 */
class ZipPack {

    static void main(_) {

        //Scanner sc = new Scanner(System.in)
        System.out.println("❤❤❤❤❤❤POS最新程序包制作中,请稍后...❤❤❤❤❤❤")
        System.out.println("版本号:" + Version.versionNumber)
        //String version = sc.nextLine()
        String version = Version.versionNumber
        /**1.项目根目录下dist文件夹**/
        File distFile = new File("dist");
        if (distFile.exists()) {//存在先删除
            distFile.deleteDir()
        }
        distFile.mkdirs()

        /**2.项目根目录下updateVersion文件夹*/
        File updateVersionFile = new File("updateVersion");
        if (!updateVersionFile.exists()) {
            System.out.print("没有程序更新！")
            System.exit(0)
        }

        /**3.将updateVersion/server目录下文件,移动到dist目录下**/
        copyDirectory("updateVersion/server", "dist")

        /**4.创建dist/update目录**/
        File updateFile = new File("dist/update");
        updateFile.mkdirs()

        File posAllFile;
        File jsonFile;

        if (version == "") { /**Version类的版本属性未设置*/
            /**5.将updateVersion/pos目录下的文件,打包成update.zip放到dist目录下**/
            updateZip("updateVersion/pos", new File("dist/update.zip"))
            posAllFile = new File("dist/update.zip")
        } else {
            /**5.将updateVersion/pos目录下的文件,打包成[release版本号.zip]放在dist目录下,并且自动生成[release版本号.json]**/
            updateZip("updateVersion/pos", new File("dist/release" + version + ".zip"))
            posAllFile = new File("dist/release" + version + ".zip")
            jsonFile = new File("dist/release" + version + ".json")

            /**6.生成[release版本号.json]文件**/
            def now = new Date()
            def yyyyMMddHHMMssSS = String.format('%1$tY-%1$tm-%1$td 06:00:10.001', now)
            jsonFile.text = """\
{
    "releaseFile":"release${version}.zip",
    "version":"${version}",
    "effectiveDate":"${yyyyMMddHHMMssSS}"
}"""
        }

        /**6.将[dist/update.zip],或者[release版本号.zip]放到每台pos下**/
        for (int j = 1; j < 2; j++) {
            File posFile = new File("dist/update/0" + j);
            posFile.mkdirs()
            copyFile(posAllFile, new File("dist/update/0" + j + "/" + posAllFile.getName()))
            if (jsonFile != null) {
                copyFile(jsonFile, new File("dist/update/0" + j + "/" + jsonFile.getName()))
            }
        }

        /**7.删除[dist/update.zip],或者[release版本号.zip]**/
        posAllFile.delete()
        if (jsonFile != null) {
            jsonFile.delete()
        }

        /**8.将dist目录压缩成server.zip**/
        updateZip("dist", new File("dist/server.zip"))

        System.out.println("❤❤❤❤❤❤制作完毕!❤❤❤❤❤❤")
    }


    static void updateZip(String srcPathName, File file) {
        File srcDir = new File(srcPathName);
        if (!srcDir.exists()) {
            throw new RuntimeException(srcPathName + "不存在!");
        }
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(file);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcDir);
        zip.addFileset(fileSet);
        zip.execute();
    }

    // 用缓冲流复制文件函数
    static void copyFile(File sourceFile, File targetFile)
            throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        int len;
        while ((len = inBuff.read()) != -1) {
            outBuff.write(len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        //关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // 复制文件夹函数
    static void copyDirectory(String sourceDir, String targetDir)
            throws IOException {

        File sourceDirFile = new File(sourceDir);
        if (!sourceDirFile.exists()) {
            return
        }
        File aimFile = new File(targetDir);
        if (!(aimFile).exists()) {    //查看目录是否存在，不存在则新建
            aimFile.mkdirs();
        }

        if (sourceDir.equalsIgnoreCase(targetDir)) {    //如果文件路径及文件名相同则覆盖
            System.out.println("文件已存在，是否覆盖（N退出/任意键继续）?");
            Scanner scanner = new Scanner(System.in);
            String NY = scanner.next();
            if (NY.equalsIgnoreCase("n")) {    //如果不想覆盖 可退出程序
                System.out.println("程序结束，感谢使用！");
                System.exit(-1);
            }

        }

        // 获取源文件夹下的文件或目录
        File oldFile = new File(sourceDir);
        File[] file = oldFile.listFiles();

        for (int i = 0; i < file.length; i++) {

            if (file[i].isFile()) //如果是文件，传递给copyFile()函数进行复制
            {
                //目标文件
                File aim = new File(targetDir);
                File targetFile = new File(aim.getAbsolutePath() + "/" + file[i].getName());
                copyFile(file[i], targetFile);
            }
            if (file[i].isDirectory()) //如果是文件夹，则递归调用
            {
                // 要递归复制的源文件夹
                String sourceFiles = sourceDir + "/" + file[i].getName();

                // 要递归复制的目标文件夹
                String aimFiles = targetDir + "/" + file[i].getName();

                copyDirectory(sourceFiles, aimFiles);
            }
        }
    }
}


