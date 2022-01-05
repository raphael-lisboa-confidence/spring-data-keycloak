package br.com.confidencecambio.aplicacao;


import br.com.confidencecambio.aplicacao.model.Endereco;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "endereco", path = "endereco")
public interface EnderecoRepository extends PagingAndSortingRepository<Endereco, Long> {
    List<Endereco> findByMunicipio(@Param("municipio") String municipio);
}