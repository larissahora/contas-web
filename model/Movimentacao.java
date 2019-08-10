package br.com.contas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Movimentacao {
	
	@Id
	@SequenceGenerator(name="movimentacaco_seq", sequenceName = "movimentacaco_seq", initialValue = 1)
	@GeneratedValue(generator="movimentacaco_seq", strategy = GenerationType.AUTO)
	private Integer id;
	private Integer tipo;
	private Integer mes;
	private Integer ano;
	private float valor;
	private String descricao;
	private Integer parcelas;
	private Integer fixa;	
	
	public Movimentacao() {
		
	}
	
	public Movimentacao(Integer id, Integer tipo, Integer mes, Integer ano, float valor, String descricao,
			Integer parcelas, Integer fixa) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.mes = mes;
		this.ano = ano;
		this.valor = valor;
		this.descricao = descricao;
		this.parcelas = parcelas;
		this.fixa = fixa;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFixa() {
		return fixa;
	}
	public void setFixa(Integer fixa) {
		this.fixa = fixa;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getParcelas() {
		return parcelas;
	}
	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
	
	
	
	


}
