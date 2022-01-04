package br.com.confidencecambio.aplicacao.ws.v1.rs;

import br.com.confidencecambio.aplicacao.ws.v1.rs.model.response.Response;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = ExemploV1RS.NOME_SERVICO, description = ExemploV1RS.DESCRICAO_SERVICO)
@RequestMapping("/v1/")
@RestController
public class ExemploV1RS {

    static final String NOME_SERVICO = "SERVICO DE EXEMPLO";
    static final String DESCRICAO_SERVICO = "Aqui você descreve o serviço";


    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid Input")
    })
    @Parameters({@Parameter(name = "auth", description = "Token de autorizacao - Parametro obrigatorio", required = true, in = ParameterIn.HEADER)})
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<String>> get() {
        return new ResponseEntity<>(new Response<>("""
                ExemploV1RS GET OK
                Com multi linhas
                """), HttpStatus.OK);
    }
}