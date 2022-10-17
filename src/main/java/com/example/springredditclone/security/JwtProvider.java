
package com.example.springredditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.example.springredditclone.exceptions.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

	
@Service
public class JwtProvider {
	private KeyStore keyStore;//Initialized in init post construct
	
	@PostConstruct
	public void init() {
		//Load input method from keystore
		try {
			keyStore=KeyStore.getInstance("JKS");//Keystore instance of jks
			InputStream resourceAsStream =  getClass().getResourceAsStream("/springblog.jks");//Getting keystore file
			keyStore.load(resourceAsStream, "secret".toCharArray());//provided to the load method plus password for the keystore
			
		}catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occured while loading keystore");
		}
	}
	
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
	}
	private PrivateKey getPrivateKey() {
		try {
			return(PrivateKey) keyStore.getKey("springblog","secret".toCharArray());//Reading Private key with alias and password for keystore, throws alot and we wrap the exception
		}catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
			throw new SpringRedditException("Exception occured while retrieving public key from keystore");
		}
	}
	public boolean validateToken(String jwt) {//Validates client token from keystore
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}

	private PublicKey getPublicKey() {//Returns Public Key from Keystore
		try {
			return keyStore.getCertificate("springblog").getPublicKey();
		}catch(KeyStoreException e) {
			throw new SpringRedditException("Exception occured while retrieving public key");
		}
	}
	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
