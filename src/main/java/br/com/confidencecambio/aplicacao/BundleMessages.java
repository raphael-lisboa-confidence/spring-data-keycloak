package br.com.confidencecambio.aplicacao;

import br.com.confidencecambio.aplicacao.ws.v1.rs.model.response.Erro;
import br.com.confidencecambio.exceptions.enums.ECodigoErro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BundleMessages {

    private static final Logger log = LoggerFactory.getLogger(BundleMessages.class);
    private static final String MENSAGEM_ERRO_PADRAO = "Erro inesperado: ";

    private final MessageSource messageSource;

    public BundleMessages(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Erro criarErro(final ECodigoErro codigoErro, final String... parametros) {

        final String codigo = codigoErro.getCodigo();
        final String mensagem = this.getMessage(codigo, parametros);

        return new Erro(mensagem, codigo);
    }

    public String getMessage(final String key, final String... args) {

        try {
            return messageSource.getMessage(key, args, Locale.getDefault());
        } catch (NoSuchMessageException ex) {
            log.error(ex.getMessage(), ex);
        }

        return MENSAGEM_ERRO_PADRAO + key;
    }
}