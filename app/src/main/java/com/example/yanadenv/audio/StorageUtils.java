package com.example.yanadenv.audio;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageUtils
{
    private static String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SamayCOVaudios";

    public static void writeToFile(String fileName, byte[] data)
    {
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File myFile = new File(fullPath, fileName);
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(data);
            fos.close();

        }    catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAudioDirectory()
    {
        return fullPath;
    }
}