package com.wanghuo.File;

/**
 * @author wangshuo
 * 这个类用来记录下载的文件的信息,文件大小,文件名,线程,以及每个线程的起始终止位置
 */
public class DownLoadFile {
//    线程数
    private int  ThreadNumber;
//    是否下载完成
    private boolean isDone;
//    文件名
    private String fileName;
//    文件的总共大小
    private long totalSize;
//    每个线程的开始位置
    private long[] startPos;
//    每个线程的结束位置
    private long[] endPos;

//构造函数
    public DownLoadFile(String fileName, long totalSize) {
        this.fileName = fileName;
        this.totalSize = totalSize;
    }

    public DownLoadFile(int threadNumber, String fileName, long totalSize) {
        ThreadNumber = threadNumber;
        this.fileName = fileName;
        this.totalSize = totalSize;
        positionCalculate();
    }

//    打印出下载文件的信息
    public void PrintFileMessage(){
        System.out.println("获取文件的信息如下:");
        System.out.println("文件名:"+fileName);
        System.out.println("文件大小:"+totalSize);
    }
//    根据文件的大小,以及线程数来计算每个线程下载的起始终止位置
    public void positionCalculate(){
//        计算起始终止位置
        int size =(int)(totalSize/ThreadNumber);

        for(int i=0;i<ThreadNumber;i++){
            startPos[i]=(i)*size;
            if(i==ThreadNumber-1)
                endPos[i]=totalSize-1;
            else
                endPos[i]=(i+1)*size-1;

        }
    }
    public long getTotalSize(){
        return  this.totalSize;
    }
    //一些get\set方法
//    判断是否完成下载
    public boolean isDone() {
        return isDone;
    }
    public int getThreadNumber() {
        return ThreadNumber;
    }
    public void setThreadNumber(int threadNumber) {
        ThreadNumber = threadNumber;
    }
    public long[] getStartPos() {
        return startPos;
    }
    public void setStartPos(long[] startPos) {
        this.startPos = startPos;
    }
    public long[] getEndPos() {
        return endPos;
    }
    public void setEndPos(long[] endPos) {
        this.endPos = endPos;
    }
}
