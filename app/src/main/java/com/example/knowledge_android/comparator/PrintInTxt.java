package com.example.knowledge_android.comparator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrintInTxt {
    public static void printInTxt(String str) {
        try {
            File file = new File("printInTxt.txt");
            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getName(), true);
            fileWritter.write(str);
            fileWritter.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
