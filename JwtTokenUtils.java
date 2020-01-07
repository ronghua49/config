package com.xyjsoft.core.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.DefaultCompressionCodecResolver;
import io.jsonwebtoken.lang.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.xyjsoft.core.bean.LoginInfo;
import com.xyjsoft.core.security.GrantedAuthorityImpl;
import com.xyjsoft.core.security.JwtAuthenticatioToken;

/**
 * JWT工具类
 *
 * @author gsh456
 * @date 2019-03-15 11:10
 */
public class JwtTokenUtils implements Serializable {

    //private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private static final String USERNAME = Claims.SUBJECT;
    /**
     * 创建时间
     */
    private static final String CREATED = "created";
    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";
    /**
     * 密钥
     */
    private static final String SECRET = "abcdefgh";
    private static final String KEY = "8ABC7DLO5MN6Z9EFGdeJfghijkHIVrstuvwWSTUXYabclmnopqKPQRxyz01234";
    private static final String DBKEY = "8ABC7DLO5MN6Z9EFGdeJfghijkHIVrstuvwWSTUXYabclmnopqKPQRxyz01234";
    /**
     * 有效期10分钟
     */

    public static final long EXPIRE_TIME = 5 * 365 * 24 * 60 * 60 * 1000L;
//	public static final long EXPIRE_TIME = Long.MAX_VALUE;

    /**
     * 无限期
     */
    public static final long MAX_EXPIRE_TIME = Long.MAX_VALUE / 2;

    /**
     * 超级用户admin token
     * sjg 2019-08-03
     */
    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6NDYxMTY4NzU5MDY4MDIyMiwiY3JlYXRlZCI6MTU3MjI1MjgzMzM1NCwiYXV0aG9yaXRpZXMiOltdfQ.IWKjhQMedwSThZ2PZNo7kbQ6aMhbOoIEWlsToKwZh0-p67rnoQDw2QQI2iXKvLz2p61125WC74aj5fyiVcSrHQ";
    public static final String admintokenOld = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6NDYxMTY4NzU3MDcxMDU3NiwiY3JlYXRlZCI6MTU1MjI4MzE4ODExOSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6InN5czp1c2VyOnZpZXcifSx7ImF1dGhvcml0eSI6InN5czptZW51OmRlbGV0ZSJ9LHsiYXV0aG9yaXR5Ijoic3lzOmRlcHQ6ZWRpdCJ9LHsiYXV0aG9yaXR5IjoiZGljdDpjb21wYW55OmFkZCJ9LHsiYXV0aG9yaXR5Ijoic3lzOmRpY3Q6ZWRpdCJ9LHsiYXV0aG9yaXR5Ijoic3lzOmRpY3Q6ZGVsZXRlIn0seyJhdXRob3JpdHkiOiJzeXM6bWVudTphZGQifSx7ImF1dGhvcml0eSI6InN5czp1c2VyOmFkZCJ9LHsiYXV0aG9yaXR5IjoiZGljdDpjb21wYW55OnZpZXcifSx7ImF1dGhvcml0eSI6InN5czpsb2c6dmlldyJ9LHsiYXV0aG9yaXR5Ijoic3lzOmRlcHQ6ZGVsZXRlIn0seyJhdXRob3JpdHkiOiJzeXM6cm9sZTplZGl0In0seyJhdXRob3JpdHkiOiJzeXM6cm9sZTp2aWV3In0seyJhdXRob3JpdHkiOiJkaWN0OmNvbXBhbnk6ZWRpdCJ9LHsiYXV0aG9yaXR5Ijoic3lzOmRpY3Q6dmlldyJ9LHsiYXV0aG9yaXR5Ijoic3lzOnVzZXI6ZWRpdCJ9LHsiYXV0aG9yaXR5Ijoic3lzOnVzZXI6ZGVsZXRlIn0seyJhdXRob3JpdHkiOiJzeXM6ZGVwdDp2aWV3In0seyJhdXRob3JpdHkiOiJzeXM6ZGVwdDphZGQifSx7ImF1dGhvcml0eSI6InN5czpyb2xlOmRlbGV0ZSJ9LHsiYXV0aG9yaXR5Ijoic3lzOm1lbnU6dmlldyJ9LHsiYXV0aG9yaXR5Ijoic3lzOm1lbnU6ZWRpdCJ9LHsiYXV0aG9yaXR5Ijoic3lzOmRpY3Q6YWRkIn0seyJhdXRob3JpdHkiOiJzeXM6cm9sZTphZGQifV19.zqeykmLqQRADkfKhQSqltd_q0llDoRfNH4FUYwcdFkWk5KbuWT9e90wZQjYMZldBDtC_XtzyvPHbsHpPJfQHSg";


    private static ObjectMapper objectMapper = new ObjectMapper();

    private static CompressionCodecResolver compressionCodecResolver = new DefaultCompressionCodecResolver();


    public static final char SEPARATOR_CHAR = '.';

    /**
     * 生成令牌
     *
     * @param authentication 用户
     * @return 令牌
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USERNAME, SecurityUtils.getUsername(authentication));
        claims.put(CREATED, new Date());
        //TODO 暂时去除权限
        claims.put(AUTHORITIES, authentication.getAuthorities());
        return generateToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        if ("admin".equals(claims.get("sub").toString())) {
            expirationDate = new Date(System.currentTimeMillis() + MAX_EXPIRE_TIME);
            JwtBuilder builder = Jwts.builder();
            JwtBuilder setClaims = builder.setClaims(claims);
            JwtBuilder setExpiration = setClaims.setExpiration(expirationDate);
            JwtBuilder signWith = setExpiration.signWith(SignatureAlgorithm.HS512, SECRET);
            String compact = signWith.compact();
            //return compact;
            return JwtTokenUtils.TOKEN;
        } else {
            JwtBuilder builder = Jwts.builder();
            JwtBuilder setClaims = builder.setClaims(claims);
            JwtBuilder setExpiration = setClaims.setExpiration(expirationDate);
            JwtBuilder signWith = setExpiration.signWith(SignatureAlgorithm.HS512, SECRET);
            String compact = signWith.compact();
            return compact;
        }
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param request
     * @return 认证信息
     */
    public static Authentication getAuthenticationeFromToken(HttpServletRequest request) {
        Authentication authentication = null;
        // 获取请求携带的令牌
        String token = JwtTokenUtils.getToken(request);
        if (token != null) {
            // 请求令牌不能为空
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            String username = claims.getSubject();
            if (username == null) {
                return null;
            }
            if (!"admin".equals(username)) {
                if (isTokenExpired(token)) {
                    return null;
                }
            }
            Object authors = claims.get(AUTHORITIES);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            if (authors != null && authors instanceof List) {
                for (Object object : (List) authors) {
                    authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
                }
            }
            authentication = new JwtAuthenticatioToken(username, null, authorities, token);
        }
        return authentication;
    }


    /**
     * admin 用户解析token认证信息
     *
     * @param request
     * @return 认证信息
     * @throws Exception
     */
    public static Authentication getMpAuthenticationeFromToken(HttpServletRequest request, LoginInfo loginInfo) {
        Authentication authentication = null;
        if (loginInfo == null) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // 请求令牌不能为空
        Claims claims = getClaimsFromToken(loginInfo.getToken());
        Object authors = claims.get(AUTHORITIES);
        if (authors != null && authors instanceof List) {
            for (Object object : (List) authors) {
                authorities.add(new GrantedAuthorityImpl((String) ((Map) object).get("authority")));
            }
        }
        authentication = new JwtAuthenticatioToken(loginInfo.getUsername(), null, authorities, loginInfo.getToken());
        return authentication;
    }

    /**
     * 宋建国 201907225
     * 普通用户解析权限认证信息
     *
     * @param request
     * @return 认证信息
     * @throws Exception
     */
    public static Authentication getMpAuthenticationeFrom(HttpServletRequest request, LoginInfo loginInfo) {
        Authentication authentication = null;
        if (loginInfo == null) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // 处理API权限认证  sjg
        if (loginInfo.getPermissions() != null) {
            for (String authority : loginInfo.getPermissions()) {
                authorities.add(new GrantedAuthorityImpl(authority));
            }
        }
        authentication = new JwtAuthenticatioToken(loginInfo.getUsername(), null, authorities, loginInfo.getToken());
        return authentication;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 验证令牌
     *
     * @param token
     * @param username
     * @return
     */
    public static Boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (token == null || token.trim().length() == 0) {
            token = request.getHeader("token");
        } else {
            if (token.contains(tokenHead)) {
                token = token.substring(tokenHead.length());
            } else if (token.startsWith("Basic ")) {
                token = request.getHeader("token");
            }
        }
        if ("".equals(token)) {
            token = null;
        }
        return token;
    }


    /**
     * 原涛 2019-12-04 13:49
     * 从令牌中获取数据声明(包含过期token)
     *
     * @param token令牌
     * @return 用户名
     */
    public static String getUsernameFromTokenNew(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 原涛 2019-12-04 13:49
     * 从令牌中获取数据声明(包含过期token)
     *
     * @param token令牌
     * @return Claims
     */
    public static Claims getClaimsFromTokenNew(String jwt) {
        Claims claims = null;
        try {
            String base64UrlEncodedHeader = null;
            String base64UrlEncodedPayload = null;
            String base64UrlEncodedDigest = null;

            int delimiterCount = 0;

            StringBuilder sb = new StringBuilder(128);

            for (char c : jwt.toCharArray()) {

                if (c == SEPARATOR_CHAR) {

                    CharSequence tokenSeq = Strings.clean(sb);
                    String token = tokenSeq != null ? tokenSeq.toString() : null;

                    if (delimiterCount == 0) {
                        base64UrlEncodedHeader = token;
                    } else if (delimiterCount == 1) {
                        base64UrlEncodedPayload = token;
                    }

                    delimiterCount++;
                    sb.setLength(0);
                } else {
                    sb.append(c);
                }
            }
            Header header = null;

            CompressionCodec compressionCodec = null;

            if (base64UrlEncodedHeader != null) {
                String origValue = TextCodec.BASE64URL.decodeToString(base64UrlEncodedHeader);
                Map<String, Object> m = readValue(origValue);
                if (base64UrlEncodedDigest != null) {
                    header = new DefaultJwsHeader(m);
                } else {
                    header = new DefaultHeader(m);
                }
                compressionCodec = compressionCodecResolver.resolveCompressionCodec(header);
            }
            String payload;
            if (compressionCodec != null) {
                byte[] decompressed = compressionCodec.decompress(TextCodec.BASE64URL.decode(base64UrlEncodedPayload));
                payload = new String(decompressed, Strings.UTF_8);
            } else {
                payload = TextCodec.BASE64URL.decodeToString(base64UrlEncodedPayload);
            }

            if (payload.charAt(0) == '{' && payload.charAt(payload.length() - 1) == '}') { //likely to be json, parse it:
                Map<String, Object> claimsMap = readValue(payload);
                claims = new DefaultClaims(claimsMap);
            }
        } catch (Exception e) {

        }
        return claims;
    }

    @SuppressWarnings("unchecked")
    protected static Map<String, Object> readValue(String val) {
        try {
            return objectMapper.readValue(val, Map.class);
        } catch (IOException e) {
            throw new MalformedJwtException("Unable to read JSON value: " + val, e);
        }
    }

}