package pers.zuqiuyu.mymonitor.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


/**
 * Created by z5139 on 2016/5/17 0017.
 */
public class IOtxt {
    /**
     * 写入ecg
     */
    public static void writeECG(String filePath,ArrayList<String> list){
        try {
            makeFile(filePath);
            File file = new File(filePath);
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            for(int i=0;i<list.size();i++){
                bw.write(list.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    /**
     * 写入record
     */
    public static void writeRecord(String filePath,ArrayList<String> list){
        try {
            makeFile(filePath);
            BufferedWriter out = null;
            File file = new File(filePath);
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            for(String arr:list){
                out.write(arr+"\n");
                out.flush();
            }
            //out.flush();
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    /**
     * 读取ECG数据
     */
    public static Double[] readECG(String filePath){
        System.out.println("IOtxt_readECG");
        ArrayList<Double> list = new ArrayList<Double>();
        File file = new File(filePath);
        if(file.exists()){
            InputStreamReader reader;
            BufferedReader br;
            try {
                reader = new InputStreamReader(new FileInputStream(file));
                br = new BufferedReader(reader);
                String lineContent = null;
                while((lineContent = br.readLine())!=null){
                    list.add(Double.parseDouble(lineContent));
                }
                br.close();
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("no this file");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("io exception");
                e.printStackTrace();
            }
        }
        return list.toArray(new Double[list.size()]);
    }
    /**
     * 读取ECG数据
     */
    public static String[] readRecord(String filePath){
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(filePath);
        if(file.exists()){
            InputStreamReader reader;
            BufferedReader br;
            try {
                reader = new InputStreamReader(new FileInputStream(file));
                br = new BufferedReader(reader);
                String lineContent = null;
                while((lineContent = br.readLine())!=null ){
                    list.add(lineContent);
                }
                br.close();
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("no this file");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("io exception");
                e.printStackTrace();
            }
        }
        return list.toArray(new String[list.size()]);
    }
    /**
     * 列出指定文件夹内路径
     */
    public static String[] listFile(String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        HashSet<String> hashSet = new HashSet<String>();
        System.out.println("该目录下对象个数：" + tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile() && tempList[i].getName().length() == 14) {
                hashSet.add(tempList[i].getName());
            }
        }
        String filesList[] = hashSet.toArray(new String[hashSet.size()]);
        return filesList;
    }
    //求中位数
    public static String getMedian(ArrayList<String> list) {
        String median = null;
        ArrayList<Double> arr=new ArrayList<Double>();
        for (int i = 0; i < list.size(); i++) {
            arr.add(Double.parseDouble(list.get(i)));
        }
        Collections.sort(arr);
        Double j = 0.0;
        if(list.size()%2==0){
            Log.e("list.size()/2)",list.size()/2+"");
            Log.e("list.size()/2-1)",(list.size()/2)-1+"");
            j=(arr.get((list.size()/2)-1)+arr.get(list.size()/2))/2;
            median = String.valueOf(j);
        }else{
            j=arr.get(list.size()/2) ;
            median = String.valueOf(j);
        }
        //median = "20";
        return median;
    }
    /**
     * String转 int 数组
     */
    public static Double[] StringToInt(String a) {
        String str[] = a.trim().split("\\s");
        Double recordInt[] = new Double[str.length];
        for (int i = 0; i < str.length; i++) {
            recordInt[i] = Double.parseDouble(str[i]);
            // recordInt[i] = Integer.parseInt(str[i]);
        }
        return recordInt;
    }
    /**
     * String转 Double 数组
     */
    public static Double[] StringToDouble(String a) {
        String str[] = a.trim().split("\n");
        Double recordInt[] = new Double[str.length];
        for (int i = 0; i < str.length; i++) {
            recordInt[i] = Double.parseDouble(str[i]);
            // recordInt[i] = Integer.parseInt(str[i]);
        }
        return recordInt;
    }


    /**
     * 列出指定文件夹内HR
     */
    public static String[] listHR(String path) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        String HR[] = new String[tempList.length];
        System.out.println("该目录下对象个数：" + tempList.length);
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                HR[i] =  tempList[i].getName().substring(20,22);
            }
        }
        return HR;
    }
    public static void deleteEmpty(String path){
        File file = new File(path);
        if (file.length() == 0){
            file.delete();
        }
    }
    //创建文件夹
    public static void makeDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
    //创建文件夹
    public static void makeFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}