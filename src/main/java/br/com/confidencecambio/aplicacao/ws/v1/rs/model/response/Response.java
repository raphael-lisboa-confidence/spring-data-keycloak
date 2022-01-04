package br.com.confidencecambio.aplicacao.ws.v1.rs.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {

    @Schema(description = "Retorno em caso de sucesso")
    private T payload;

    @Schema(description = "Codigos de erro em caso de falha na requisição")
    private List<Erro> erros;

    public Response() {
    }

    public Response(final T payload) {
        this.payload = payload;
    }

    public Response(final List<Erro> errorCodes) {
        this.erros = errorCodes;
    }

    public Response(final Erro... errorCodes) {
        this.erros = Arrays.asList(errorCodes);
    }

    public T getPayload() {
        return payload;
    }

    public List<Erro> getErros() {
        return erros;
    }
}