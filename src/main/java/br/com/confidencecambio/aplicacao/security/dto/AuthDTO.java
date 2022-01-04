package br.com.confidencecambio.aplicacao.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthDTO {

    @JsonProperty("usuario")
    private final String user;

    @JsonProperty("senha")
    private final String password;

    public AuthDTO(final String user,
                   final String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
