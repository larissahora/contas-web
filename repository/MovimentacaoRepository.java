package br.com.contas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.contas.model.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer>{
//	@Query(value = "insert into Movimentacao (descricao, mes, ano, dia, valor, parcelas, tipo, fixa) VALUES (:descricao, :mes, :ano, :dia, :valor, :parcelas, :tipo)", nativeQuery = true)
//    void saveConta(@Param("descricao") String descricao, @Param("mes") Integer mes,
//    		@Param("ano") Integer ano, @Param("dia") Integer dia, @Param("valor") float valor, @Param("parcelas") Integer parcelas, @Param("fixa") boolean fixa);
//	
	@Query(value = "insert into movimentacao (ano, descricao, fixa, mes, parcelas, tipo, valor, id) values (:ano, :descricao, :fixa, :mes, :parcelas, :tipo, :valor)", nativeQuery = true)
		void cadastrarConta(Integer ano, String descricao, Integer fixa, Integer mes, Integer parcelas, Integer tipo,
			float valor);
}
