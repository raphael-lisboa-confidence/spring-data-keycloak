package br.com.confidencecambio.aplicacao.security.auth;

import br.com.confidencecambio.aplicacao.security.dto.AuthDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.util.Collections;


@Service
public class AuthRepositoryImpl implements IAuthRespository {

    private static final Logger LOG = LoggerFactory.getLogger(AuthRepositoryImpl.class);
    private final RestOperations restTemplate;
    private final Environment env;

    public AuthRepositoryImpl(final RestOperations restTemplate,
                              final Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Override
    public String auth(final String user, final String password) {
        String token = "";
        final String uriDadosBasicos = getUri();
        final ResponseEntity<String> response =
                restTemplate.exchange(uriDadosBasicos, HttpMethod.POST, buildHttpEntity(user, password), String.class);
        final HttpHeaders headers = response.getHeaders();
        if(headers != null){
            token = headers.getFirst("auth");
        }
        return token;
    }

    private HttpEntity buildHttpEntity(final String user, final String password) {
        final AuthDTO autDTO = new AuthDTO(user, password);
        return new HttpEntity<>(autDTO, buildHeader());
    }

    private HttpHeaders buildHeader() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String getUri() {
        final String baseUri = env.getProperty("auth.autenticarUsuario");
        if (StringUtils.isEmpty(baseUri)) {
            LOG.error("Uri do Auth nao foi informada");
            throw new IllegalArgumentException("Uri do Auth nao foi informada");
        }
        return baseUri;
    }
}