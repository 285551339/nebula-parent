package com.xy.nebula.commons.utils.cipher;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5通用类
 * 
 * @author gaozhenhai
 * @since 2013.01.15
 * @version 1.0.0_1
 */
public class MD5 {
    /**
     * MD5方法
     * 
     * @param text
     *            明文
     * @param key
     *            密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        byte[] bytes = (text + key).getBytes(StandardCharsets.UTF_8);

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        bytes = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                sb.append("0");
            }

            sb.append(Long.toString(bytes[i] & 0xff, 16));
        }

        return sb.toString().toLowerCase();
    }

    /**
     * MD5验证方法
     * 
     * @param text
     *            明文
     * @param key
     *            密钥
     * @param md5
     *            密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        String md5Text = md5(text, key);
        return md5Text.equalsIgnoreCase(md5);
    }

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}