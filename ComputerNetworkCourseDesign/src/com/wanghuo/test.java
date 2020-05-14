package com.wanghuo;

import com.wanghuo.Socket.SingleThread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class test {
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        mulThreads mulThreads = new mulThreads();
//        mulThreads.run();
        RandomAccessFile testFile = new RandomAccessFile("D:\\test\\QQMusic_YQQWinPCDL.exe","rwd");
        byte[] buffer = new byte[1024*10];
        for(int i =0;i<3;i++){
            String fileName = "D:\\test\\QQMusic_YQQWinPCDL.exe"+i;
            RandomAccessFile part = new RandomAccessFile(fileName,"rwd");
            int len=0;
            while((len=part.read(buffer))!=-1){
                testFile.write(buffer,0,len);
            }
        }
    }
}
class mulThreads extends Thread{
    public void run(){
        SingleThread[] singleThread = new SingleThread[3];
        for(int i =0;i<3;i++){
            singleThread[i] = new SingleThread(i);
            Thread th =new Thread(singleThread[i]);
            th.start();
        }
    }


}

class single implements  Runnable{
    private String ThreadName;
    public single(String ThreadName){
        this.ThreadName = ThreadName;
    }
    public void run(){
        int count =0;
        while(count<100) {
            System.out.println(this.ThreadName + " is running! times:"+count);
            if(count%20==0){
                Thread.yield();
            }
            count++;
        }
    }
}
