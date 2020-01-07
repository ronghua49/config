package com.xyjsoft.core.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *    google身份验证器的原理是什么呢？客户端和服务器事先协商好一个密钥K，用于一次性密码的生成过程，此
 * 密钥不被任何第三方所知道。此外，客户端和服务器各有一个计数器C，并且事先将计数值同步。进行验证时，客户端对
 * 密钥和计数器的组合(K,C)使用HMAC（Hash-based Message Authentication Code）算法计算一次性密码，公式如下：
 *  	HOTP(K,C) = Truncate(HMAC-SHA-1(K,C))  
 * 	      上面采用了HMAC-SHA-1，当然也可以使用HMAC-MD5等。HMAC算法得出的值位数比较多，不方便用户输入，因
 * 此需要截断（Truncate）成为一组不太长十进制数（例如6位）。计算完成之后客户端计数器C计数值加1。用户将这一组十
 * 进制数输入并且提交之后，服务器端同样的计算，并且与用户提交的数值比较，如果相同，则验证通过，服务器端将计数值
 * C增加1。如果不相同，则验证失败。
 * */
public class GoogleAuthenticator {  
	private Logger logger = LoggerFactory.getLogger(GoogleAuthenticator.class);
    // 生成的key长度( Generate secret key length)  
    public static final int SECRET_SIZE = 10; 
  
    public static final String SEED = "g8GjEvTbW5oVSV7avL47357438reyhreyuryetredLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";  
    // Java实现随机数算法  
    public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";  
    // 最多可偏移的时间  
    int window_size = 3; // default 3 - max 17  
  
    public void setWindowSize(int s) {      
        if (s >= 1 && s <= 17)  
            window_size = s;  
    }  
    
    public static String generateSecretKey() {  
        SecureRandom sr = null;  
        try {  
            sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);  
            sr.setSeed(Base64.decodeBase64(SEED));  
            byte[] buffer = sr.generateSeed(SECRET_SIZE);  
            Base32 codec = new Base32();  
            byte[] bEncodedKey = codec.encode(buffer);  
            String encodedKey = new String(bEncodedKey);  
            return encodedKey;  
        } catch (NoSuchAlgorithmException e) {  
            // should never occur... configuration error  
        }  
        return null;  
    }  
  
   
    public static String getQRBarcodeURL(String user, String host, String secret) {  
        String format = "http://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s?secret=%s";  
        return String.format(format, user, host, secret);  
    }  
  
    /** 
     * 生成一个google身份验证器，识别的字符串，只需要把该方法返回值生成二维码扫描就可以了。 
     *  
     * @param user 
     *            账号 
     * @param secret 
     *            密钥 
     * @return 
     */  
    public static String getQRBarcode(String user, String secret) {  
        String format = "otpauth://totp/%s?secret=%s";  
        return String.format(format, user, secret);  
    }  
  
    public boolean check_code(String secret, long code, long timeMsec) {  
        Base32 codec = new Base32();  
        byte[] decodedKey = codec.decode(secret);  
        long t = (timeMsec / 1000L) / 30L;  
        for (int i = -window_size; i <= window_size; ++i) {  
            long hash;  
            try {  
                hash = verify_code(decodedKey, t + i);  
            } catch (Exception e) {  
                logger.error("[GoogleAuthenticator:check_code]",e);
                throw new RuntimeException(e.getMessage());  
            }  
            if (hash == code) {  
                return true;  
            }  
        }  
        return false;  
    }  
    
    public static String generatePaycode(String key,Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
    	long X = 30;
    	long t = 0L;
    	if(timestamp == null || timestamp.equals(0L)){
    		t = System.currentTimeMillis()/1000;
    	}else{
    		t = timestamp/1000;
    	}
		long T = t / X;
    	return String.valueOf(verify_code(new Base32().decode(key),T));
    }
    
    public static int generateCode(String key) throws NoSuchAlgorithmException, InvalidKeyException {
    	long X = 30;
		long t = System.currentTimeMillis()/1000;
		long T = t / X;
    	return verify_code(new Base32().decode(key),T);
    }
    
    private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {  
        byte[] data = new byte[8];  
        long value = t;  
        for (int i = 8; i-- > 0; value >>>= 8) {  
            data[i] = (byte) value;  
        }  
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");  
        Mac mac = Mac.getInstance("HmacSHA1");  
        mac.init(signKey);  
        byte[] hash = mac.doFinal(data);  
        int offset = hash[20 - 1] & 0xF;  
        long truncatedHash = 0;  
        for (int i = 0; i < 4; ++i) {  
            truncatedHash <<= 8;  
            truncatedHash |= (hash[offset + i] & 0xFF);  
        }  
        truncatedHash &= 0x7FFFFFFF;  
        truncatedHash %= 1000000;  
        return (int) truncatedHash;  
    }  
}  