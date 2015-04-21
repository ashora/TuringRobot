package org.nupter.turingrobot.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.nupter.turingrobot.model.ChatMessage;
import org.nupter.turingrobot.model.Result;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by panl on 15/4/20.
 */
public class HttpUtils {

    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "c806e3d69b682d4585384c37aaa6bac5";

    /**
     * 发送一个消息，得到返回的消息
     * @param jsonRes
     * @return
     */
    public static ChatMessage sendMessage(String jsonRes){

        ChatMessage chatMessage = new ChatMessage();
        Gson gson = new Gson();
        Result result = null;
        try {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());
        }catch (JsonSyntaxException e){
            chatMessage.setMsg("服务器繁忙，请稍后再试！");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

//    public static void doGet(String msg){
//        String url = setParams(msg);
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                String jsonRes = new String(responseBody);
//                HttpUtils.sendMessage(jsonRes);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
////        InputStream is = null;
////        ByteArrayOutputStream byteArrayOutputStream = null;
////        try {
////            java.net.URL getUrl = new URL(url);
////            HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
////            connection.setReadTimeout(5 * 1000);
////            connection.setConnectTimeout(5 * 1000);
////            connection.setRequestMethod("GET");
////            is = connection.getInputStream();
////            int length = -1;
////            byte[] buf = new byte[1024];
////            byteArrayOutputStream = new ByteArrayOutputStream();
////            while ((length = is.read(buf)) != -1){
////                byteArrayOutputStream.write(buf,0,length);
////            }
////            result = new String(byteArrayOutputStream.toByteArray());
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }finally {
////            if (byteArrayOutputStream != null){
////                try {
////                    byteArrayOutputStream.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////
////            if (is != null){
////                try {
////                    is.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////
////        }
////        Log.i("TAG",result);
//
//    }

    public static String setParams(String msg) {

        String url = null;
        try {
            url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }


}
