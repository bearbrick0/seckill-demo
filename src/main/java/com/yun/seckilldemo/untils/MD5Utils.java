package com.yun.seckilldemo.untils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

/**
 * @author wanglufei
 * @title: MD5Utils
 * @projectName seckill-demo
 * @description: TODO
 * @date 2021/10/30/8:49 下午
 */
@Component
public class MD5Utils {
    private static final String salt = "1a2b3c4d";
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    /**
     * 固定salt
     * 第一次MD5加密，用于网络传输
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass){
        //避免在网络传输被截取然后反推出密码，所以在md5加密前先打乱密码
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 随机salt
     * 第二次MD5加密，用于存储到数据库
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //后段真正调用的方法
    public static String inputPassToDbPass(String input, String saltDB){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }
    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        //d3b1294a61a07da9b49b6e22b2cbd7f9
        //d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToDbPass("123456","1a2b3c4d"));
        //b7797cce01b4b131b433b6acf4add449
    }
}
