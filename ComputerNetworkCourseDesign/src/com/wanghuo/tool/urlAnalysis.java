package com.wanghuo.tool;

import com.wanghuo.URL.url;

import java.util.ArrayList;
import java.util.List;

public class urlAnalysis {

    public  static  url analysisUrl(String urlString){
//协议是否合法  https://studygolang.com/dl/golang/go1.14.2.src.tar.gz
        int port=443;
        String protocol="https";
        String host,path,fileName;
        List<String> protocolSet = new ArrayList<>();
        protocolSet.add("http");
        protocolSet.add("https");

        //长度不合法需要 处理异常
        if(urlString.length()<=0)
            errorDeal.errorPrint("输入的长度为零！请从新输入");

//        判断协议，以及是否有://
//        不接受没有协议名的url
        String str;
        if(urlString.matches(".*://.*")) {
            String[] strings = urlString.split("://");
            if (protocolSet.contains(strings[0].trim().toLowerCase()))
                protocol = strings[0].trim().toLowerCase();
            else {
                //协议出错 捕捉到异常
                errorDeal.errorPrint("URL中协议非HTTP或HTTPs，不可处理！请从新输入~！");
            }
            str = strings[1].trim();
        }else{
            str = urlString.trim();
            System.out.println("urlString"+urlString);
            errorDeal.errorPrint("暂时不接受没有协议名的url！");
        }
//        http协议则修改port
        if(protocol.toLowerCase().equals("http"))
            port=80;
        int position=0;
        while(position<str.length()&&str.charAt(position)!='/'){
            position++;
        }
        //可能存在 路径为 / 的，后续在考虑 即便是这种情况也需要文件名
        if(position==0||position==str.length())
            errorDeal.errorPrint("输入的URL格式不对。缺少主机或路径文件！请从新输入");
        if(str.substring(0,position).matches(".*:.*")){
            host = str.substring(0,position).split(":")[0];
            port = Integer.parseInt(str.substring(0,position).split(":")[1].trim());
        }
        else
            host=str.substring(0,position);

        path=str.substring(position,str.length());

//        从path中解析filename
        int i = path.length()-1;
        while(i>0){
            if(path.charAt(i)=='/')
                break;
            i--;
        }
        if(i==path.length()-1)
            errorDeal.errorPrint("未找到请求资源文件名称！");
        fileName = path.substring(i+1);
        url url = new url(protocol,host,port,fileName,path);
        return url;
    }







}
