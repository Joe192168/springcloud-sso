package com.joe.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class EncryptUtil {

    public static String encodeBase64(byte[] bytes){
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return encoded;
    }

    public static byte[]  decodeBase64(String str){
        byte[] bytes = null;
        bytes = Base64.getDecoder().decode(str);
        return bytes;
    }

    public static String encodeUTF8StringBase64(String str)throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
    }

    public static String  decodeUTF8StringBase64(String str) throws UnsupportedEncodingException{
        byte[] bytes = Base64.getDecoder().decode(str);
        return new String(bytes,"utf-8");
    }

    public static String encodeURL(String url) throws UnsupportedEncodingException{
        return URLEncoder.encode(url, "utf-8");
	}


	public static String decodeURL(String url) throws UnsupportedEncodingException{
        return URLDecoder.decode(url, "utf-8");
	}

    public static void main(String [] args) throws UnsupportedEncodingException{
        String str = "abcd{'a':'b'}";
        String encoded = EncryptUtil.encodeUTF8StringBase64(str);
        String decoded = EncryptUtil.decodeUTF8StringBase64(encoded);
        System.out.println(str);
        System.out.println(encoded);
        System.out.println(decoded);

        String url = "== wo";
        String urlEncoded = EncryptUtil.encodeURL(url);
        String urlDecoded = EncryptUtil.decodeURL(urlEncoded);
        
        System.out.println(url);
        System.out.println(urlEncoded);
        System.out.println(urlDecoded);
    }


}
