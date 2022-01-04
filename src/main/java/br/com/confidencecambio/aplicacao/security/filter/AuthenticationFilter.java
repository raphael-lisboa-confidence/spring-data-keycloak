package br.com.confidencecambio.aplicacao.security.filter;

import br.com.confidencecambio.auth.library.validator.JwtTokenValidator;
import br.com.confidencecambio.auth.library.validator.TokenData;
import br.com.confidencecambio.auth.library.validator.exception.ExpiredTokenException;
import br.com.confidencecambio.auth.library.validator.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final String TOKEN_HEADER_PARAM = "Auth";

    private final Environment env;

    public AuthenticationFilter(final Environment env) {
        this.env = env;
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {
        try {
            final Authentication authentication = getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
            LOGGER.debug("Token expirado!");
        } catch (InvalidTokenException e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            LOGGER.error("Erro ao assinar token!", e);
        }
    }

    private Authentication getAuthentication(final HttpServletRequest request)
            throws InvalidTokenException, ExpiredTokenException {

        final String token = request.getHeader(TOKEN_HEADER_PARAM);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        final TokenData tokenData = new JwtTokenValidator(env.getProperty("auth.key")).obterDadosToken(token) ;

        final List<GrantedAuthority> authorities = tokenData.getScopes().stream()
                                                      .map(SimpleGrantedAuthority::new)
                                                      .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(tokenData.getLogin(), null, authorities);
    }
}