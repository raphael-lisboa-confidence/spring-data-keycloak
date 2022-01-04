package br.com.confidencecambio.aplicacao.security.auth;

public interface IAuthRespository {
    String auth(final String user, final String password);
}
