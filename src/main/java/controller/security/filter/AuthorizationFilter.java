//package controller.security.filter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Date;
//
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//@Component
//public class AuthorizationFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//            String authorizationHeader = request.getHeader(AUTHORIZATION);
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                try {
//                    String token = authorizationHeader.substring("Bearer ".length());
//                    Algorithm algorithm = Algorithm.HMAC256("dickcheese".getBytes());
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//                    DecodedJWT decodedJWT = verifier.verify(token);
//                    String email = decodedJWT.getSubject();
//                    UsernamePasswordAuthenticationToken authenticationToken
//                            = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    filterChain.doFilter(request, response);
//                } catch (IllegalArgumentException e) { //TODO work with exceptions
//                    e.printStackTrace();
//                } catch (JWTVerificationException e) {
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
//                }
//            } else {
//                filterChain.doFilter(request, response);
//            }
//
//
//    }
//}
