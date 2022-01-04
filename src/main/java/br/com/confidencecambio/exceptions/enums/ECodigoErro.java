package br.com.confidencecambio.exceptions.enums;

public enum ECodigoErro {

    /* Generic domain exceptions */
    ERRO_NAO_MAPEADO("000"),
    ERRO_INTERNO("001");

    private final String codigo;

    ECodigoErro(final String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}