package com.eci.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImp {

    public static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";

    public String createToken(String userId) {            // public String createToken(ObjectId userId
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            String token = JWT.create()
                    .withClaim("userId", userId.toString())
                    .withClaim("createdAt", new Date())
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
            //log WRONG Encoding message
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            //log Token Signing Failed
        }
        return null;
    }

    public String getUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
            //log WRONG Encoding message
            return null;
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            //log Token Verification Failed
            return null;
        }
    }

    public boolean isTokenValid(String token) {
    	
    	// PreparedStatement preparedStmt = null;
    	 Connection conn = null;
    	 Statement stmt = null;
    	 ResultSet rs	= null;
    	 boolean flag=false;
    	
    	String userId=getUserIdFromToken(token);
    	
    	String sql ="SELECT * FROM eci.user_auth_token where userId= '"+userId+"' and IsActive='Y';";
    			
    			System.out.println("----sql getAll users----"+sql);
    				try
    				{
    					conn = GetDBConnection.getConnection();
    					stmt = conn.createStatement(); 
    					rs = stmt.executeQuery(sql);
    					while(rs.next())
    					{
    						flag=true;
    					}
    				}
    				
    				catch(Exception e)
    		    	{
    		    		e.printStackTrace();
    		    	}
    	
    	
    	return flag;
       /* String userId = this.getUserIdFromToken(token);
        return userId != null;*/
    }
}