package com.v2x.springbootoauth2authorizationserver;

import com.v2x.springbootoauth2authorizationserver.properties.OAuth2Properties;
import com.v2x.springbootoauth2authorizationserver.utils.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@RestController
@SpringBootApplication
@Slf4j
public class SpringbootOauth2AuthorizationServerApplication {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OAuth2Properties oAuth2Properties;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOauth2AuthorizationServerApplication.class, args);
    }

    @GetMapping("/userJwt")
    public Object getCurrentUserJwt(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("【SecurityOauth2Application】 getCurrentUserJwt authentication={}", JsonUtil.toJson(authentication));

        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey(oAuth2Properties.getJwtSigningKey().getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String blog = (String) claims.get("blog");
        log.info("【SecurityOauth2Application】 getCurrentUser1 blog={}", blog);

        return authentication;
    }

    @GetMapping("/userRedis")
    @PreAuthorize("hasAnyAuthority('select')")
    public Object getCurrentUserRedis(Authentication authentication) {
        log.info("【SecurityOauth2Application】 getCurrentUserRedis authentication={}", JsonUtil.toJson(authentication));
        return authentication;
    }
}
