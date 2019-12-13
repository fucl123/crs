package com.kzkj.utils;

import java.io.*;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

public class Base64aaaaa {
    /**
     * 将字符串 s 进行 BASE64 编码
     */
    public static String encode(String s) {
        if (s == null)
            return null;
        String res = "";
        try {
            res = new sun.misc.BASE64Encoder().encode(s.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 将 BASE64 编码的字符串 s 进行解码
     */
    public static String decode(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b,"utf-8");
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        //System.out.println(Base64aaaaa.encode(""));
        String path = "C:\\Users\\32723\\Desktop\\跨境终端报文\\";
        String str = Base64aaaaa.decode("");
        String str1 = "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";

        FileInputStream is = new FileInputStream("C:\\Users\\32723\\Desktop\\跨境终端报文\\IVN101.xml");
        byte[] b = new byte[1024];
        int len = 0;
        while((len=is.read(b))!=-1){
            //System.out.println(new String(b,0,len));
        }
        is.close();

        byte[] data= Base64.decodeBase64(str1);
        //System.out.println(data);
        //System.out.println(b);
        //getFile(sb,"C:\\Users\\32723\\Desktop\\跨境终端报文\\","file");
        writeBytesToFile(str1,path+"file");

//        System.out.println("-----------------------------------------------------------------");
//        String xmlFilePath = "C:\\Users\\32723\\Desktop\\跨境终端报文\\SAS121.xml";
//        String xml = fileRead(xmlFilePath);
//        byte[] zipbyte = zip(xml.getBytes(),"49f306b0-506d-465a-bca5-5711qqq3a333.xml");
//        String dataxml = new String(Base64.encodeBase64(zipbyte),"utf-8");
//        System.out.println("反编译加密报文："+dataxml);
        //writeBytesToFile(str1,path+"file");
    }

    public static void writeBytesToFile(String str,String path) throws IOException{
        byte[] data= Base64.decodeBase64(str);
        if(data != null){
            String filepath = path;
            File file  = new File(filepath);
            if(file.exists()){
                file.delete();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data,0,data.length);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * bfile 需要转换成文件的byte数组
     * filePath  生成的文件保存路径
     * fileName  生成文件后保存的名称如test.pdf，test.jpg等
     */
    public static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            boolean isDir = dir.isDirectory();
            if (!isDir) {// 目录不存在则先建目录
                try {
                    dir.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static byte[] zip(byte[] data,String name) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry(name);
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    public static String fileRead(String path) throws Exception {
        File file = new File(path);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }
}