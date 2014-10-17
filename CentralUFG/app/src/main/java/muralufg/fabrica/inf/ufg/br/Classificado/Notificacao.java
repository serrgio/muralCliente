package br.ufg.inf.mobile2014.projetoufg;

/**
 * Classe bean que modela uma notificação.
 * 
 * @author eric
 */
public class Notificacao {
	private int foiLido;
	private int id;
	private String remetente;
	private String titulo;
	private String conteudo;
	private String tipo;
	private String dataHora;

	/**
	 * Construtor da classe.
	 * 
	 * @param titulo
	 *            título da notificação
	 * @param remetente
	 *            remetente da notificação
	 * @param conteudo
	 *            conteúdo da notificação
	 * @param tipo
	 *            tipo da notificação (pública ou privada)
	 * @param dataHora
	 *            data e hora da criação da notificação
	 */
	public Notificacao(String titulo, String remetente, String conteudo,
			String tipo, String dataHora) {
		this.titulo = titulo;
		this.remetente = remetente;
		this.conteudo = conteudo;
		this.tipo = tipo;
		this.dataHora = dataHora;
		this.foiLido = 0;
	}

	/*
	 * Getters e Setters
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getRementente() {
		return remetente;
	}

	public String getConteudo() {
		return conteudo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDataHora() {
		return dataHora;
	}

	public int getFoiLido() {
		return foiLido;
	}

	public void setFoiLido(int foiLido) {
		this.foiLido = foiLido;
	}
}
