package com.zhang.file;

import java.io.File;

public class Temp {

    public static void main(String[] args) {
        String path = "/Volumes/SL-Zhang/MIJIA_RECORD_VIDEO/";
        try {
            File[] files = new File(path).listFiles();
            for (File file : files) {
                System.out.println(file.getName());
                if (file.isDirectory()){
                    File[] subFiles = file.listFiles();
                    for (File subFile : subFiles) {
                        if (subFile.getName().endsWith(".jpeg")){
                            subFile.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
