package com.wanghuo.File;



import java.io.File;
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

    public void deletePartFile(int ThreadNumber){
        for(int i=0;i<ThreadNumber;i++){
            File partFile = new File(this.LocalPath+i);
            if(!partFile.delete())
                System.out.println(partFile.getName()+"删除失败~~");;
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
