package authApp.Middleware;

import io.jsonwebtoken.*;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class verifyToken implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;

        String token = req.getHeader("Authorization");
        if (token.equals("")) {
            throw new JwtException("Missing Token");
        }


        // method parseClaimsJws will throw UnsupportedJwtException if token invalid
        Jws<Claims> tokenParsed = Jwts.parser()
                .setSigningKey(System.getenv("JWT_KEY").getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);

        Claims tokenBody = tokenParsed.getBody();
        req.setAttribute("user", tokenBody);

        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
