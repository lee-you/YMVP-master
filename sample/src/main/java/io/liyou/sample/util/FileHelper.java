package io.liyou.sample.util;

import android.app.Activity;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.liyou.sample.App;
import io.liyou.sample.general.RequestListener;
import io.liyou.sample.model.entity.FileEntity;

/**
 * Created by jiana on 16-11-21.
 * 文件帮助类 <br />
 * file helper class
 */

public class FileHelper {

    /**
     * assets压缩包文件名 <br />
     * zip file name in assets folder
     */
    public static final String FILE_NAME = "data.zip";
    public static final int BUFFER_SIZE = 1024;
    /**
     * 文件层数
     */
    public static int fileLayer = 0;

    /**
     * 解压
     */
    public void unzip(Activity activity, final RequestListener<List<FileEntity>> listener) {
        try {
            unzipModuleData(new File(getAppCacheDir()),
                    App.getInstance().getAssets().open(FILE_NAME));

            File file = new File(getAppCacheDir());
            final List<FileEntity> fileEntities = new ArrayList<>();
            for (File f : file.listFiles()) {
//                FileEntity fileEntity = new FileEntity();
//                fileEntity.setFile(f);
//                fileEntity.setLevel(0);
//                fill(fileEntity, f);
                fileEntities.add(fill(new FileEntity(), f));
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.requestSuccess(fileEntities);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileEntity fill(FileEntity fileEntityResult, File file) {
//        Log.e("file", fileLayer + ";folder = " + file.isDirectory() + ";file = " + file.toString());
//        FileEntity fileEntity = new FileEntity();
        fileEntityResult.setFile(file);
        fileEntityResult.setLevel(fileLayer);
        if (file.isDirectory()) {
            fileLayer++;
            for (File f : file.listFiles()) {
                fileEntityResult.addSubItem(fill(new FileEntity(), f));
            }
            fileLayer--;
        }
        return fileEntityResult;
//        fileEntityResult.addSubItem(fileEntity);
    }

    /**
     * 解压
     *
     * @param directory
     * @param ism
     * @throws Exception
     */
    public void unzipModuleData(final File directory, InputStream ism) throws Exception {
        InputStream is = new BufferedInputStream(ism);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
        try {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String entryName = entry.getName();
                if (!entryName.isEmpty()) {
                    if (entry.isDirectory()) {
                        File file = new File(directory, entryName);
                        if (file.exists()) continue;
                        if (!file.mkdir()) {
                            throw new RuntimeException("Failed to create directory");
                        }

                    } else {
                        int count;
                        byte[] buff = new byte[BUFFER_SIZE];
                        BufferedOutputStream dest = null;
                        try {
                            OutputStream fos = new FileOutputStream(new File(directory, entryName));
                            dest = new BufferedOutputStream(fos, BUFFER_SIZE);
                            while ((count = zis.read(buff, 0, BUFFER_SIZE)) != -1) {
                                dest.write(buff, 0, count);
                            }
                            dest.flush();
                        } finally {
                            if (dest != null) {
                                dest.close();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            zis.close();
        }
    }

    public void readText(Activity activity, File file, final RequestListener<String> listener) {
        try {
            FileReader fr = new FileReader(file);
            final StringBuilder sb = new StringBuilder();
            int len;
            char[] buff = new char[1024];
            while ((len = fr.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
            fr.close();

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.requestSuccess(sb.toString());
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存目录 <br />
     * Get cache folder
     * @return
     */
    public String getAppCacheDir() {
        String cachePath = null;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || !Environment.isExternalStorageRemovable()) {
//            File f = App.getInstanse().getExternalCacheDir();
//            cachePath = App.getInstanse().getExternalCacheDir().getPath();
//        } else {
            cachePath = App.getInstance().getCacheDir().getPath();
//        }
        return cachePath;
    }

}


