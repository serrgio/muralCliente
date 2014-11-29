package br.ufg.inf.mobile2014.projetoufg.Banco;

import java.util.ArrayList;

import br.ufg.inf.mobile2014.projetoufg.Notificacao;

/**
 * Interface para operações de registro de usuários e recebimento de notificações no servidor.
 * 
 * @author eric
 */
public interface LoginServidor {

	public boolean loginNoServidor(String usuario, String senha);
	
	public boolean registraUsuarioNoServidor(String usuario, String senha, String email, String tipoUsuario, String disciplinas);
	
	public boolean updateCadastro(String usuario, String senha, String email, String tipoUsuario, String disciplinas);
	
	public boolean pesquisaNomeNoServidor(String usuario);
	
	public ArrayList<Notificacao> receberNotificacoesDoServidor();
}
