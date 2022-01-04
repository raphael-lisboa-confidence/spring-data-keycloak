package br.com.confidencecambio.aplicacao.ws.v1.rs;

import br.com.confidencecambio.aplicacao.BundleMessages;
import br.com.confidencecambio.aplicacao.ws.v1.rs.model.response.Erro;
import br.com.confidencecambio.aplicacao.ws.v1.rs.model.response.Response;
import br.com.confidencecambio.exceptions.enums.ECodigoErro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebServiceHandlerExceptions {

    private final BundleMessages bundleMessages;
    private static final Logger log = LoggerFactory.getLogger(WebServiceHandlerExceptions.class);


    public WebServiceHandlerExceptions(BundleMessages bundleMessages) {
        this.bundleMessages = bundleMessages;
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Response> handlerErroNaoMapeado(final Throwable e) {

        log.error(e.getMessage(), e);

        Erro erro = bundleMessages.criarErro(ECodigoErro.ERRO_NAO_MAPEADO);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<Void>(erro));
    }
}
