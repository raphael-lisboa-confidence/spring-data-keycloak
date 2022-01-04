package br.com.confidencecambio.aplicacao.ws.v1.rs.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "informações de erro")
public class Erro {

    private String codigo;
    private String mensagem;

    public Erro() {
    }

    public Erro(final String mensagem) {
        this.mensagem = mensagem;
    }

    public Erro(final String mensagem, final String codigo) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
