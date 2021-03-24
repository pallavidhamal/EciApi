package com.eci.util;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AndroidPush {

    /**
     * Replace SERVER_KEY with your SERVER_KEY generated from FCM
     * Replace DEVICE_TOKEN with your DEVICE_TOKEN
     */
    private static String SERVER_KEY = "AAAAaoYBGNM:APA91bHAkqJXvRgBVWxRqRjzrOhJHKu-u3YQapcOG5zOh-H5IjxSg6ztAuo6VhidxqC3Nd9TTs6Z7CEBiW08Zx8c3Bdd5MqkmYywh1So4Hh1iDvZ19pJLCGTC4ow2xHbsAx55HYZX4hq";
    private static String DEVICE_TOKEN = "dwl8bXiIc6Q:APA91bHeq-5-Z6Ku7qXsLEnMsjDK4j13kXkxM_jfTMR2iIKIAPmrNmCOw9qdnSn9ttO18ticC4KSe7mAffOFeH7V2dbGpQYP37aIaioE__SVnOHLPJy7j5_SAImESV-mdH-Xv69W8ZkE";


    /**
     * USE THIS METHOD to send push notification
     */
    public static void main(String[] args) throws Exception {
        String title = "My First Notification";
        String message = "Hello, I'm push notification";
        sendPushNotification(title, message);
    }


    /**
     * Sends notification to mobile, YOU DON'T NEED TO UNDERSTAND THIS METHOD
     */
    private static void sendPushNotification(String title, String message) throws Exception {
        String pushMessage = "{\"data\":{\"title\":\"" +
                title +
                "\",\"message\":\"" +
                message +
                "\"},\"to\":\"" +
                DEVICE_TOKEN +
                "\"}";
        // Create connection to send FCM Message request.
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Send FCM message content.
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(pushMessage.getBytes());
        
        System.out.println(conn.getResponseCode());
        System.out.println(conn.getResponseMessage());
    }
}