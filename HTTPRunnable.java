package com.mirea.kt.nika;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;


public class HTTPRunnable implements Runnable{
    private String address;
    private HashMap<String,String> requestBody;
    private String responseBody;

    public HTTPRunnable(String address, HashMap<String, String> requestBody) {
        this.address = address;
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }
    private String generateStringBody(){
        StringBuilder sbParams = new StringBuilder();
        if(this.requestBody != null && !requestBody.isEmpty()){
            int i =0;
            for(String key : this.requestBody.keySet()){
                try{
                    if (i != 0){
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=").append(URLEncoder.encode(this.requestBody.get(key), "UTF-8"));
                }
                catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                i++;
            }

        }
        return sbParams.toString();
    }

    @Override
    public void run() {
        if(this.address != null && ! this.address.isEmpty()){
            try{
                URL url = new URL(this.address);
                URLConnection connection = url.openConnection();//Возвращае экземпляр класса URLConnection
                HttpURLConnection httpConnection = (HttpURLConnection)connection;//
                httpConnection.setRequestMethod("POST");//Установили тип запроса
                httpConnection.setDoOutput(true);
                OutputStreamWriter osw = new OutputStreamWriter(httpConnection.getOutputStream());
                osw.write(generateStringBody());
                osw.flush();
                int responseCode = httpConnection.getResponseCode();
                System.out.println("ResponceCode :" + responseCode);
                if(responseCode == 200){
                    InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());//считывает поток данных из открытого соединения
                    BufferedReader br = new  BufferedReader(isr);//хранит считанные значения(Специальный класс наследуется от Reader)
                    String currentLine;
                    StringBuilder sbResponce = new StringBuilder();//Класс для работы со строками
                    while((currentLine = br.readLine())!= null){
                        sbResponce.append(currentLine);//Считываем и записываем все что пришло
                    }
                    responseBody =  sbResponce.toString();
                }else{
                    System.out.println("Error! Bad response code!");
                }

            }
            catch(IOException iex){
                System.out.println("Erorrs" + iex.getMessage());

            }
        }


    }
}
