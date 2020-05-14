package com.wanghuo.File;

public class DownLoadFile {
    private int  ThreadNumber;
    private boolean isDone;
    private String fileName;
    private long totalSize;
    private long[] startPos;
    private long[] endPos;


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

    public void PrintFileMessage(){
        System.out.println("获取文件的信息如下:");
        System.out.println("文件名:"+fileName);
        System.out.println("文件大小:"+totalSize);
    }
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
