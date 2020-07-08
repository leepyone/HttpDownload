package com.wanghuo;

import com.wanghuo.Socket.SingleThread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        mulThreads mulThreads = new mulThreads();
//        mulThreads.run();
//        RandomAccessFile testFile = new RandomAccessFile("D:\\test\\test.exe","rwd");
//        byte[] buffer = new byte[1024*10];
//        for(int i =0;i<4;i++){
//            String fileName = "D:\\test\\BaiduNetdisk_6.9.7.4.exe"+i;
//            RandomAccessFile part = new RandomAccessFile(fileName,"rwd");
//            int len=0;
//            while((len=part.read(buffer))!=-1){
//                testFile.write(buffer,0,len);
//            }
//        }
        Scanner sc = new Scanner(System.in);
        List<String>  lineList = new ArrayList<>();
        while( sc.hasNextLine()){
            String line = sc.nextLine();
            if(line.isEmpty())
                break;
            lineList.add(line);
        }
        System.out.println(lineList.size());
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
