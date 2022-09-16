package com;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lkp
 * @describe
 * @date 2022/9/16
 */
@Configuration
public class RSAUtils {
    private static Log log = LogFactory.getLog(RSAUtils.class);
    private static final String KEYALGORITHM = "RSA";
    private static final String SIGNATUREALGORITHM = "MD5withRSA";

    private static final String PUBLICKEY = "RSAPublicKey";
    private static final String PRIVATEKEY = "RSAPrivateKey";

    //rsa私钥  或者可从配置文件读取。
    /**
     * RSA私钥
     */
    public static final String DECRYPTPRIVATEKEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDEbXO3tVb9U2TUokLsC6giesxkpN/SHpLrEFpE6ALPwXfF0QjB1MEauNQoiEgUJ4Ovp6x+JXFSl0J2JG2poJL0h1c770WzaXp/HqgaQAD2mfEhYvtvDm8DHIr6WK7sFOgwWFR2LJF0Hp6sLWD9/ANUHLAJaFjjAJTb9NKxMENCG4ZnPL6GZkYQHSyqBltsml6ixbzt01kWiaf+4aF8IMnOzCNL/HQ5cP3LKTlMSrdAS/HmlYJwEmoCgF8MNDLl5PdFHlgEYMk4MepbVzY7DB2vJsQRcoj1EJmaw3IizZ5mfXPULDUB1H6jB1epsl/0Cczd7UI3wZylYsPI6dpHKYuBAgMBAAECggEALB8tzAkmthaCYqTkBWOE6+HLgZvrTqZhd/2fWTUIVKRvg/a1Udy5V4hG16ftEOHjWZR/UYKVlui1HBaIr36LI/Q2qxbPAELkKiO3vNW9oabonAhA1CxY0UkbCqra99qmYR2mMzlFnuC14ZT74XE3SjB7dlGDDdJ2nYW7weNGaFWjg+IyUNAgcpjtVzMr7l735w3UA3z8eqS4hQdTOKzzVv88pi9eNclQXvFapxXmsIlwB9/LiVfeaazIlvBf9Mi+oawSBFcJzRpnWLlNkaTNUQQLsUgL5m93iobIB2PfFwd85+HrrSRXZjxCqiOq7/LrfNdcZuCQ6+VsHelqL7fzVQKBgQDu/urOAGVeDxtjY9x87XTrhgxqVTP6bYojMhNz5yJBbsOalo34ck3KAC0h/tn+g7BnU5v/pA5UB5vxyr44hjTjZ67vnGxvVKHTgKzQNPjmK61aguaLGmwybYXIPSQJi/BwhkF+xdyHY6zL7GJIJo2BBtnwiO7bfyA91GPNaHahIwKBgQDSZzHZ7vrifv3tPBGA12clEKAE0nHXzmInFskhbSIT7okNnTTzOlr4PqJCvEIIdn0Hdu1q6vsQi3zdLvDkqwteVvH1/UPfqy41uCtyDwHT0bTFF733uXAlaErIlnPALJrSYSdBKbD4TFmbyJxUd1Hor5FXUCtcVLPxAMHNEDBVCwKBgDMp436CWzENruFSKYudEiCxq8q/vsbn2GC8gamYW58CZWGcxXvgjIKoEwFqG6T+8C7OmWDa8C5RCrNyodJb+MqOEcc+B4C174blzxkrnfU4eH4d89jnqS4KgE9jt2lLygds1mybUyCw1/c3/bpQdo6ett1lkyzuRCq4Yz1AFdVdAoGBAKnNxMrIdsuyh4Ydc1bTAJhGOY6KtHvhQQ+g8QO8CsOhQBSvqhseHmq4ScWoH3arAa6ziVo/eA3bNRNgneM+M3eYnb/T/SIXCb0q/E0Z1XS4iraBubuieeKGr8wAuDwBA5YD+GU7m2pYW/1AwwHcJkuu3ZJkzgm/BlXbMY0oookPAoGAflu8/RdnDEWD+7CN3uMQ4+jtdAOoyRgqmVVcRGy+cYWRucCkDvXuuYKyXBdF220cjde1X5ixta7a2wgrrv+GZXBttObVesaXT0cCj8XzD2mDJZ6gNZYJ+cvMTXH2SYC3n9yWGWBxNce9l8ZxPHhodHDrDWdL/Iw2m2HcJ8JUdPE=";
    //公钥
    public static final String DECRYTPIBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxG1zt7VW/VNk1KJC7AuoInrMZKTf0h6S6xBaROgCz8F3xdEIwdTBGrjUKIhIFCeDr6esfiVxUpdCdiRtqaCS9IdXO+9Fs2l6fx6oGkAA9pnxIWL7bw5vAxyK+liu7BToMFhUdiyRdB6erC1g/fwDVBywCWhY4wCU2/TSsTBDQhuGZzy+hmZGEB0sqgZbbJpeosW87dNZFomn/uGhfCDJzswjS/x0OXD9yyk5TEq3QEvx5pWCcBJqAoBfDDQy5eT3RR5YBGDJODHqW1c2OwwdrybEEXKI9RCZmsNyIs2eZn1z1Cw1AdR+owdXqbJf9AnM3e1CN8GcpWLDyOnaRymLgQIDAQAB";


    private RSAUtils() {
        super();
    }

    public static byte[] decryptBASE64(String key) {
        Base64 base64 = new Base64();
        return base64.decode(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        Base64 base64 = new Base64();
        return base64.encodeToString(bytes);
    }

    /**
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) {
        try {

            byte[] keyBytes = decryptBASE64(privateKey);

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance(KEYALGORITHM);

            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

            Signature signature = Signature.getInstance(SIGNATUREALGORITHM);
            signature.initSign(priKey);
            signature.update(data);
            return encryptBASE64(signature.sign());
        } catch (Exception e) {
            log.error("RSAUtilsSignError");
            return "";
        }
    }

    /**
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) {
        try {

            byte[] keyBytes = decryptBASE64(publicKey);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance(KEYALGORITHM);

            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATUREALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);

            return signature.verify(decryptBASE64(sign));
        } catch (Exception e) {
            log.error("RSAUtilsVerifySignError");
            return false;
        }
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) {
        try {

            byte[] keyBytes = decryptBASE64(key);

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEYALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("RSAUtilsPrivateKeyDecryptError");
            return new byte[0];
        }
    }

    /**
     * 根据私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key) {
        return decryptByPrivateKey(decryptBASE64(data), key);
    }

    /**
     * 跟据公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) {
        try {

            byte[] keyBytes = decryptBASE64(key);

            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEYALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("RSAUtilsPublicKeyDecryptError");
            return new byte[0];
        }

    }

    /**
     * 根据公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key) {
        try {

            byte[] keyBytes = decryptBASE64(key);

            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEYALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            log.error("RSAUtilsPublicKeyEncryptError");
            return new byte[0];
        }
    }

    /**
     * 根据私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) {
        try {

            byte[] keyBytes = decryptBASE64(key);

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEYALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("RSAUtilsPrivateKeyEncryptError");
            return new byte[0];
        }
    }

    /**
     * 根据初始化的key获取私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap) {
        if (keyMap != null) {
            Key key = keyMap.get(PRIVATEKEY);
            return encryptBASE64(key.getEncoded());
        } else {
            return "";
        }
    }

    /**
     * 根据初始化的key获取公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap) {
        if (keyMap != null) {
            Key key = keyMap.get(PUBLICKEY);
            return encryptBASE64(key.getEncoded());
        } else {
            return "";
        }
    }

    /**
     * 初始化key
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator
                    .getInstance(KEYALGORITHM);
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            Map<String, Key> keyMap = new HashMap(2);
            keyMap.put(PUBLICKEY, keyPair.getPublic());
            keyMap.put(PRIVATEKEY, keyPair.getPrivate());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            log.error("RSAUtilsInitKeyError");
            return null;
        }
    }



    public static Map<String, Object> getTwoKeys() {
        Map<String, Key> keyMap = initKey();
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);

        Map<String, Object> params = new HashMap<>();
        params.put("publicKey", publicKey);
        params.put("privateKey", privateKey);
        return params;
    }


    public static String decryptByPrivateKeyNew(String password) {
        byte[] pass = decryptByPrivateKey(decryptBASE64(password), DECRYPTPRIVATEKEY);
        return new String(pass);
    }

    /**
     * main 方法测试解密
     * @param args
     */
    public static void main(String[] args) {
        String password = "v1Xovo8IidoKfuuA30Ow9sH0U7vxmPVUMKrMJwuG7mmowvtbPydB2rP5KFMC57dHqdsXATBvKT8RpftEB7+Pi8il2oFyCRnkgybrXPMLxeLI07B4kb4h0HHpaABbWlxrmEGkCu4voEmx/kMZR15us7dChljG+IERFdkvpna4oN9tq3NxkyvmXC4PtR7aBbl9jkH9OdWR3Vb6PJ6idE9OJPjPldWyKr47L2jjz4hW53x4E2CkCO1hMbGA0v8A8SWzKmPFmmyPmyEJhkOOvZix/skwlOsYL0HJB1jxZ+t04gTaqlaZYpliYTABLvYlqH4pCEnT5LtvjPk2d19C8cREeg==";
        System.out.println(RSAUtils.decryptByPrivateKeyNew(password));
    }
}