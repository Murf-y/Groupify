package com.murfy.groupify.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

public class ImageEncoding {
    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString(byteArray, 0);
    }

    public static Bitmap convertToBitmap(String base64String) {
        if(base64String == null || base64String.length() == 0) return null;
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
