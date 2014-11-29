package br.ufg.inf.mobile2014.projetoufg;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a ExpandableList dos títulos das notificações.
 * Esses títulos representam o conjunto de título, remetente e data que
 * aparece no fragment da main, listando as notificações.
 * 
 * @author eric
 *
 */
public class TituloNotificacoesExpandableList {

	private Notificacao notificacao;
	public final List<String> children = new ArrayList<String>();
	
	/**
	 * Construtor da classe.
	 * 
	 * @param notificacao	o objeto notificação
	 */
	public TituloNotificacoesExpandableList(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	/*
	 * Getter
	 */
	public Notificacao getNotificacao() {
		return notificacao;
	}	
}
