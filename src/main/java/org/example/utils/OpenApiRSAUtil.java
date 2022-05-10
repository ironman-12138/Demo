package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * RSA签名工具类
 * Created by 刘志强 on 2017/4/24.
 */
@Slf4j
public class OpenApiRSAUtil {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger("apiLogger");

    private static final int    DEFAULT_BUFFER_SIZE       = 8192;

    private static final String SIGN_CHARSET              = "UTF-8";

    private static final String SIGN_TYPE_RSA             = "RSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    /**
     * RSA2签名
     *
     * @param map 待签名数据和签名字段字符串
     * @return 签名
     * @throws Exception 异常上层处理
     */
    public static String rsaSign(Map<String, String> map,String privateKey) throws Exception {
        String content = getSignContent(map);
        String reqSeq = map.get("reqSeq");
        log.info(">>待签名数据为:" + reqSeq + "," + content);
        PrivateKey priKey = getPrivateKeyFromPKCS8(
            new ByteArrayInputStream(privateKey.getBytes()));
        Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(SIGN_CHARSET));
        String sign = new String(Base64.encodeBase64(signature.sign()));
        log.info(">>签名为:" + reqSeq + "," + sign);
        return sign;
    }

    /**
     * RSA2验签
     *
     * @param map 待验签数据、签名信息和签名字段字符串
     * @return 验签结果 true/false
     * @throws Exception 异常上层处理
     */
    public static boolean rsaCheck(Map<String, String> map,String publicKey) throws Exception {
        String content = getSignContent(map);
        String reqSeq = map.get("reqSeq");
        String sign = map.get("sign");
        log.info(">>待验证的签名为:" + reqSeq + "," + sign);
        log.info(">>生成签名的参数为:" + reqSeq + "," + content);
        PublicKey pubKey = getPublicKeyFromX509(new ByteArrayInputStream(publicKey.getBytes()));
        Signature signature = Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(SIGN_CHARSET));
        boolean signResult = signature.verify(Base64.decodeBase64(sign.getBytes()));
        log.info(">>验签结果:" + reqSeq + "," + signResult);
        return signResult;
    }

    /**
     * 把参数按照ASCII码递增排序，如果遇到相同字符则按照第二个字符的键值ASCII码递增排序
     * 将排序后的参数与其对应值，组合成“参数=参数值”的格式，并且把这些参数用&字符连接起来
     *
     * @param sortedParams 待签名数据和签名字段字符串
     * @return 待签名字符串
     */
    private static String getSignContent(Map<String, String> sortedParams) {
        //appId,method,bizContent,生成签名所需的参数
        String[] sign_param = sortedParams.get("sign_param").split(",");
        List<String> keys = new ArrayList<>();
        Collections.addAll(keys, sign_param);
        Collections.sort(keys);
        StringBuilder content = new StringBuilder();
        int index = 0;
        for (String key : keys) {
            String value = sortedParams.get(key);
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    private static PrivateKey getPrivateKeyFromPKCS8(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
        byte[] encodedKey = readText(ins).getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    private static PublicKey getPublicKeyFromX509(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
        StringWriter writer = new StringWriter();
        io(new InputStreamReader(ins), writer, -1);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private static String readText(InputStream ins) throws IOException {
        Reader reader = new InputStreamReader(ins);
        StringWriter writer = new StringWriter();
        io(reader, writer, -1);
        return writer.toString();
    }

    private static void io(Reader in, Writer out, int bufferSize) throws IOException {
        if (bufferSize == -1) {
            bufferSize = DEFAULT_BUFFER_SIZE >> 1;
        }
        char[] buffer = new char[bufferSize];
        int amount;
        while ((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }
    }
}
