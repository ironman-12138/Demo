package org.example.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version V1.0
 * @desc AES 加密工具类
 */
public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法
    //自定义密码
    private static final String ASSETS_DEV_PWD_FIELD = "xxxx";

    public static String getAssetsDevPwdField() {
        return ASSETS_DEV_PWD_FIELD;
    }

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return Base64Utils.encodeToString(result);//通过Base64转码返回
        } catch (Exception ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            //执行操作
            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
            String s = new String(result, "utf-8");
            return s;
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(String password) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            //AES 要求密钥长度为 128
            kg.init(128, random);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt1(String content, String password) {
        try {
            //这里是128bit密钥，所以是 16 byte,如果是其它长度的 除以8 ，修改，这里简单演示一下
            byte[] newkey = new byte[16];
            for (int i = 0; i < newkey.length && i < password.getBytes().length; i++) {
                newkey[i] = password.getBytes()[i];
            }

            SecretKeySpec key = new SecretKeySpec(newkey, "AES");
            // 创建密码器，AES默认是/ECB/PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] byteContent = content.getBytes("utf-8");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密成16进制字符串
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encryptToHex(String content, String password) {
        return parseByte2HexStr(encrypt1(content, password));
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt1(byte[] content, String password) {
        try {
            //这里是128bit密钥，所以是 16 byte,如果是其它长度的 除以8 ，修改，这里简单演示一下
            byte[] newkey = new byte[16];
            for (int i = 0; i < newkey.length && i < password.getBytes().length; i++) {
                newkey[i] = password.getBytes()[i];
            }

            SecretKeySpec key = new SecretKeySpec(newkey, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            // 加密
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从16进制内容中解密
     *
     * @param hexContent 16进制的加密内容
     * @param password   密码
     * @return
     */
    public static String decryptFromHex(String hexContent, String password) {
        return decrypt1(parseHexStr2Byte(hexContent), password);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String origin = "李志明";
        String encrypt = AESUtil.encrypt(origin, "12345678");
        String decrypt = AESUtil.decrypt("IdHuQK7nJ4aNs1hUsgtGpw==", "637472126871071");
        System.out.println(origin);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }

}
