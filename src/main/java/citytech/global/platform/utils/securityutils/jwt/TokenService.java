package citytech.global.platform.utils.securityutils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Singleton
public class TokenService {
    private final Key key;
    public TokenService() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    public String generateToken(String userId, String emailId, String roles) {
        long now = System.currentTimeMillis();
        long exp = now + (1000 * 60 * 60); // 1 hour expiration

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", userId);
        payload.put("emailId", emailId);
        payload.put("roles", roles);
        payload.put("exp", new Date(exp));

        return Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .signWith(key)
                .compact();
    }
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Key getKey() {
        return key;
    }
    public RequestContext getUserContextFromRequest(String token){
        if(token!=null){
            Claims claims = parseToken(token);
            int subject  = claims.get("sub", Integer.class);
            String emailId = claims.get("emailId", String.class);
            String roles = claims.get("roles", String.class);
            return new RequestContext(subject, emailId, roles);
        }
        else{
            return new RequestContext();
        }
    }
}