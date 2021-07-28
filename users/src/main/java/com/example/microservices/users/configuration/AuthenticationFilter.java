package com.example.microservices.users.configuration;

import com.example.microservices.users.api.model.LoginRequestModel;
import com.example.microservices.users.dto.UserDto;
import com.example.microservices.users.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final UsersService usersService;
  private final AppConfig appConfig;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      LoginRequestModel creds = new ObjectMapper()
          .readValue(request.getInputStream(), LoginRequestModel.class);
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.getEmail(),
              creds.getPassword(),
              new ArrayList<>()
          )
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String userName = ((User) authResult.getPrincipal()).getUsername();
    UserDto userDetails = usersService.getUserDetailsByEmail(userName);

    Long exp = appConfig.getTokenSettings().getExpirationInMilliseconds();
    String token = Jwts.builder()
        .setSubject(userDetails.getUserId())
        .setExpiration(new Date(System.currentTimeMillis() + exp))
        .signWith(SignatureAlgorithm.HS512, appConfig.getTokenSettings().getSecret())
        .compact();
    response.addHeader("token", token);
    response.addHeader("userId", userDetails.getUserId());
  }
}
