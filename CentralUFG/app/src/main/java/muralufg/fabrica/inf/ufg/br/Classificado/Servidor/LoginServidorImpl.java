package br.ufg.inf.mobile2014.projetoufg.Servidor;

import java.util.ArrayList;

import br.ufg.inf.mobile2014.projetoufg.Notificacao;
import br.ufg.inf.mobile2014.projetoufg.Banco.LoginServidor;

/**
 * Classe responsável pelo login e cadastro de usuários no servidor, além do envio de notificações.
 * Atualmente, as operações de usuário estão inativas, e seus métodos só retornam true.
 * 
 * @author eric
 */
public class LoginServidorImpl implements LoginServidor {

	private ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();

	@Override
	public boolean loginNoServidor(String usuario, String senha) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean registraUsuarioNoServidor(String usuario, String senha,
			String email, String tipoUsuario, String disciplinas) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean updateCadastro(String usuario, String senha, String email,
			String tipoUsuario, String disciplinas) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean pesquisaNomeNoServidor(String usuario) {
		// TODO Auto-generated method stub
		return true;
	}

	public void notificacaoFromServidor(Notificacao notificacao) {
		notificacoes.add(notificacao);
	}

	@Override
	public ArrayList<Notificacao> receberNotificacoesDoServidor() {
		return notificacoes;
	}

}
