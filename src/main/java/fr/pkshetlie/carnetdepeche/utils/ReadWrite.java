/*
 * Copyright (c) 2014. Les scripts sont la propriété exclusive de pierrick pobelle, toutes modifications ou revente est interdite.
 */

package fr.pkshetlie.carnetdepeche.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class ReadWrite {
    private String Dir = "";
    private String fileName = "";

    public ReadWrite(String dir, String filename) {
        Dir = dir;
        fileName = filename;
        System.setProperty("file.encoding", "UTF8");
    }

    public String readFile() throws IOException {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + Dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        Log.v("carnet.readfile", file.getAbsolutePath());
        if (!file.exists()) {
            file.createNewFile();
        }
        FileInputStream fis;
        String str = "";

        try {
            String ch;
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((ch = br.readLine()) != null) {
                str += ch;
            }
            fis.close();
            br.close();
            isr.close();
            return str;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void writeFile(String str) throws IOException {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + Dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(str);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
