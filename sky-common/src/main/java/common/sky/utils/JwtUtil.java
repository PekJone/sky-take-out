package common.sky.utils;

import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-04-28  10:31
 */
public class JwtUtil {


    public static String createJwt(String secretKey, long ttlMillis, Map<String,Object> map){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return "";
    }

}
