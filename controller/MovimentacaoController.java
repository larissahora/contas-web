package br.com.contas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.contas.model.Movimentacao;
import br.com.contas.repository.MovimentacaoRepository;

@RestController
public class MovimentacaoController {
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	
	
	@CrossOrigin(origins = "*")	
	@GetMapping("/conta")
	public List<Movimentacao> contas(){
		return movimentacaoRepository.findAll();
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/cadastrar")
	public void cadastrar(@RequestBody Movimentacao movimentacao) {
		movimentacaoRepository.save(movimentacao);
	}	


	@CrossOrigin(origins = "*")
	@PutMapping("/atualizar")
	public void editar(@RequestBody Movimentacao mov ) {
		 movimentacaoRepository.save(mov);
	}
	
	@CrossOrigin(origins = "*")
	@DeleteMapping("/pagar/{id}")
	public void pagar(@PathVariable("id") Integer id) {
		movimentacaoRepository.deleteById(id);
	}
}