package com.wanghuo.File;



import java.io.IOException;
import java.io.RandomAccessFile;

public class LocalFile {
    private String fileName;
    private String LocalPath;
    private RandomAccessFile randomAccessFile;


    public LocalFile(String fileName,String LocalPath,long pointer) throws IOException {
        String fullPath = LocalPath+"\\"+fileName;
        this.fileName =fileName;
        this.LocalPath = fullPath;
        randomAccessFile= new RandomAccessFile(fullPath,"rwd");
        randomAccessFile.seek(pointer);
    }
    public String getLocalPath(){
        return this.LocalPath;
    }
    public String getFileName(){ return  this.fileName;}
    public  void setPointer(long pointer){
        try{
            randomAccessFile.seek(pointer);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public synchronized int write(byte[] buffer,int start,int end){
        int count =-1;
        try{
           randomAccessFile.write(buffer, start, end);
//            System.out.println("执行了write");
            count=end;
        }catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }

    public void close(){
        if(randomAccessFile!=null)
            try{

                randomAccessFile.close();
            }catch (IOException e){

                e.printStackTrace();
            }
    }
    public void donePrint(){
        try {
            System.out.println("文件大小为" + randomAccessFile.length());
        }catch ( IOException e){
            e.printStackTrace();
        }
    }
//setLength 的作用？
    public void setLength(long length){
        try {
            if (randomAccessFile.length() != length)
                randomAccessFile.setLength(length);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
