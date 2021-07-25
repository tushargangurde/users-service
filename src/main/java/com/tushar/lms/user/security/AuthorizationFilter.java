package com.tushar.lms.user.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tushar.lms.user.exception.CustomJwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

		String authorizationHeader = request.getHeader("Authorization");
		try {
			if (authorizationHeader == null) {
				return null;
			}
			String userId = null;
			String token = authorizationHeader.replace("Bearer ", "");

			Claims body = Jwts.parser().setSigningKey("secret_key").parseClaimsJws(token).getBody();

			// userId =
			// Jwts.parser().setSigningKey("secret_key").parseClaimsJws(token).getBody().getSubject();

			userId = body.getSubject();
			String role = (String) body.get("role");

			if (userId == null) {
				return null;
			}

			// GetUserResponse userResponse = userService.getUser(userId);
			List<GrantedAuthority> authorities = Arrays.stream(role.split(",")).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			return new UsernamePasswordAuthenticationToken(userId, null, authorities);
		} catch (JwtException exception) {
			throw new CustomJwtException(exception.getMessage());
		}
	}

}
