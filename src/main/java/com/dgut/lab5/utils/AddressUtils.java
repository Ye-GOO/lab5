package com.dgut.lab5.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddressUtils {

    /**
     * @param urlStr
     *            请求的地址
     * @param encoding
     *            服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    private String getResult(String urlStr, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒，如果运行时出现超时，可自行增大超时时间，如加到10000
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("POST");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口
            DataOutputStream out = new DataOutputStream(connection
                    .getOutputStream());// 打开输出流往对端服务器写数据
            out.flush();// 刷新
            out.close();// 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }

    /**
     *
     * @param encodingString
     *            服务器端请求编码。如GBK,UTF-8等
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAddresses(String encodingString)
            throws UnsupportedEncodingException {
        // 这里调用PC-Online的接口
        String urlStr = "http://whois.pconline.com.cn/ip.jsp";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = this.getResult(urlStr, encodingString);

        if (returnStr != null) {
            // 处理返回的省市区信息
            return returnStr;
        }
        return null;
    }

    // 这里我们举的例子是获取所在地省份名称，也可以改变上边getAddress的返回值，获取具体的市县名
    public static String getProvinceName(){
        AddressUtils addressUtils = new AddressUtils();
        String address = "";
        try {
            address = addressUtils.getAddresses("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String addres = address.substring(0,address.length()-3);

        return addres;
    }

}
