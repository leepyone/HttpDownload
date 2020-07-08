package com.wanghuo.File;

/**
 * 这个类主要对本地文件的存取与操作
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LocalFile {
    private String fileName;
//    下载文件存储到本地的路径
    private String LocalPath;
//    创建的file类用来将读取的文件内容写入
    private RandomAccessFile randomAccessFile;

//构造函数,完成file类的初始化
    public LocalFile(String fileName,String LocalPath,long pointer) throws IOException {
        String fullPath = LocalPath+"\\"+fileName;
        this.fileName =fileName;
        this.LocalPath = fullPath;
        randomAccessFile= new RandomAccessFile(fullPath,"rwd");
        randomAccessFile.seek(pointer);
    }
//    get函数
    public String getLocalPath(){
        return this.LocalPath;
    }
    public String getFileName(){ return  this.fileName;}
//    设置file文件的指针
    public   void setPointer(long pointer){
        try{
            randomAccessFile.seek(pointer);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
//    将内容写入到本地文件,并上锁了
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
//关闭file文件
    public void close(){
        if(randomAccessFile!=null)
            try{

                randomAccessFile.close();
            }catch (IOException e){

                e.printStackTrace();
            }
    }
//    检查读取的文件大小的信息是否与正确的信息吻合
    public void doneCheckSize(long totalSize){
        try {
            if(totalSize!=randomAccessFile.length())
                System.out.println("下载的文件与获取的信息中文件的大小不同，请重试！");
            else
                System.out.println("下载的文件与获取的信息中文件的大小相同，文件大小为" + randomAccessFile.length());
        }catch ( IOException e){
            e.printStackTrace();
        }
    }
//删除中间产生的组合文件
    public void deletePartFile(int ThreadNumber){
        for(int i=0;i<ThreadNumber;i++){
            File partFile = new File(this.LocalPath+i);
            if(!partFile.delete())
                System.out.println(partFile.getName()+"删除失败~~");;
        }
    }

//setLength 的作用？ 设置了file文件的大小
    public void setLength(long length){
        try {
            if (randomAccessFile.length() != length)
                randomAccessFile.setLength(length);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
