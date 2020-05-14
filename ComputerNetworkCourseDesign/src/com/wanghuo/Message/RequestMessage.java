package com.wanghuo.Message;

import com.wanghuo.URL.url;
import com.wanghuo.tool.errorDeal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestMessage {
    private long totalSize;
    private long timeStamp;

    private url url;

    public RequestMessage(url url){
        this.url = url;
    }

    public  String getHeadMessage(){
        StringBuffer message = new StringBuffer();
        message.append("HEAD "+url.getPath()+" HTTP/1.1\r\n");
        message.append("Host: " + url.getHost() + " \r\n");
        message.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36" + "\r\n");
        message.append("Connection: Keep-Alive "  + "\r\n");
        message.append("\r\n");
        return message.toString();
    }

//    对head请求的报文进行解析，获得文件size
    public void HeadMessageAnalysis(List<String> HeadMessageList){
        String str = HeadMessageList.get(0);
        int status =0;
        Pattern pattern = Pattern.compile("\\s\\d{3}\\s");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find())
            status = Integer.parseInt(matcher.group(0).trim());
        //处理头部判断是否可以下载。
        if(status!=200)
            errorDeal.errorPrint("状态码为："+status);
        String fileType=null,fileLength=null,fileName=null;
        for (String  s :HeadMessageList) {
            if(s.matches("^Content-Type.*"))
                fileType=s.split(":")[1];

            if(s.matches(".*Content-Length.*"))
                fileLength=s.split(":")[1];

        }
        if(fileType!=null&&fileLength!=null)
            this.totalSize = Integer.parseInt(fileLength.trim());
        else
            errorDeal.errorPrint("fileLength、fileType解析错误");


    }
    public long getTotalSize(){
        return  this.totalSize;
    }

    public String getGetMessage(long start,long end){
        StringBuffer message = new StringBuffer();
        message.append("GET "+url.getPath()+" HTTP/1.1\r\n");
        message.append("Host: " + url.getHost() + " \r\n");
        message.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36" + "\r\n");
        message.append("Connection: Keep-Alive "  + "\r\n");
        message.append("Range: bytes= " +start+"-"+end + "\r\n");
        message.append("\r\n");
        return message.toString();
    }

}
