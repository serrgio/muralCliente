package br.ufg.inf.mobile2014.projetoufg.Banco;

import java.util.ArrayList;

import br.ufg.inf.mobile2014.projetoufg.Notificacao;
import br.ufg.inf.mobile2014.projetoufg.Servidor.FabricaNotificacoes;
import br.ufg.inf.mobile2014.projetoufg.Servidor.LoginServidorImpl;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Classe para login e cadastro de usuários. Esta classe realiza login, cadastro
 * e consulta de nomes de usuários no banco de dados local, além de solicitar
 * que as mesmas operações sejam feitas remotamente no servidor.
 * 
 * @author eric
 */
public class BDAdapter {

	private static final String BD_TABLE_USUARIOS = "usuarios";
	private static final String BD_TABLE_NOTIFICACOES = "notificacoes";
	public static final String KEY_USUARIOS_ID = "_id";
	public static final String KEY_USUARIOS_USUARIO = "usuario";
	public static final String KEY_USUARIOS_SENHA = "senha";
	public static final String KEY_USUARIOS_EMAIL = "email";
	public static final String KEY_USUARIOS_TIPO_USUARIO = "tipo_usuario";
	public static final String KEY_USUARIOS_DISCIPLINAS = "disciplinas";
	public static final String KEY_NOTIF_ID = "_id";
	public static final String KEY_NOTIF_TITULO = "titulo";
	public static final String KEY_NOTIF_REMETENTE = "remetente";
	public static final String KEY_NOTIF_CONTEUDO = "conteudo";
	public static final String KEY_NOTIF_TIPO = "tipo_notificacao";
	public static final String KEY_NOTIF_DATA_HORA = "data_hora";
	public static final String KEY_NOTIF_JA_FOI_LIDO = "ja_foi_lido";
	private SQLiteDatabase banco;
	private Context context;
	private BDHelper bdHelper;
	private LoginServidorImpl loginServidor = new LoginServidorImpl();

	/**
	 * Construtor da classe
	 * 
	 * @param context
	 */
	public BDAdapter(Context context) {
		this.context = context;
	}

	/**
	 * Método que abre uma conexão com o banco.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public BDAdapter open() throws SQLException {
		bdHelper = new BDHelper(context);
		banco = bdHelper.getWritableDatabase();

		return this;
	}

	/**
	 * Método que fecha a conexão com o banco.
	 */
	public void close() {
		bdHelper.close();
	}

	/**
	 * Método usado para registrar o usuário e sua senha no banco.
	 * Primeiramente, a operação é feita no servidor. Se bem sucedida, o
	 * registro é feito também localmente, no banco.
	 * 
	 * @param usuario
	 *            o nome do usuário
	 * @param senha
	 *            a senha do usuário
	 * @return -1 para erro, outro número para sucesso
	 * @throws SQLException
	 */
	public long registrar(String usuario, String senha, String email,
			String tipoUsuario, String disciplinas) throws SQLException {
		if (loginServidor.registraUsuarioNoServidor(usuario, senha, email,
				tipoUsuario, disciplinas)) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_USUARIOS_USUARIO, usuario);
			initialValues.put(KEY_USUARIOS_SENHA, senha);
			initialValues.put(KEY_USUARIOS_EMAIL, email);
			initialValues.put(KEY_USUARIOS_TIPO_USUARIO, tipoUsuario);
			initialValues.put(KEY_USUARIOS_DISCIPLINAS, disciplinas);

			return banco.insert(BD_TABLE_USUARIOS, null, initialValues);
		} else {
			return -1;
		}
	}

	/**
	 * Método usado para fazer update do cadastro de usuários.
	 * Primeiramente, a operação é feita no servidor. Se bem sucedida, o
	 * update é feito também localmente, no banco.
	 * 
	 * @param usuario		o nome do usuário
	 * @param senha			a senha do usuário
	 * @param email			o email do usuário
	 * @param tipoUsuario	o tipo de usuario (público ou privado)
	 * @param disciplinas	as disciplinas onde o usuário está cadastrado
	 * @return				-1 para erro, outro número para sucesso
	 * @throws SQLException
	 */
	public long updateCadastro(String usuario, String senha, String email,
			String tipoUsuario, String disciplinas) throws SQLException {
		if (loginServidor.updateCadastro(usuario, senha, email, tipoUsuario,
				disciplinas)) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_USUARIOS_USUARIO, usuario);
			initialValues.put(KEY_USUARIOS_SENHA, senha);
			initialValues.put(KEY_USUARIOS_EMAIL, email);
			initialValues.put(KEY_USUARIOS_TIPO_USUARIO, tipoUsuario);
			initialValues.put(KEY_USUARIOS_DISCIPLINAS, disciplinas);

			return banco.update(BD_TABLE_USUARIOS, initialValues,
					KEY_USUARIOS_ID + " = ?",
					new String[] { String.valueOf(getId(usuario)) });
		} else {
			return -1;
		}
	}

	/**
	 * Método usado para efetuar o login do usuário. A operação deve ser feita
	 * no servidor. Como não temos um, o login está sendo realizado no banco.
	 * Essa consulta local, no entanto, pode ou não existir. Talvez seja
	 * interessante manter uma consulta local para fins de performance. Porém,
	 * vulnerebilidades de segurança podem surgir, caso essa opção seja adotada.
	 * 
	 * @param usuario
	 *            o nome do usuário
	 * @param senha
	 *            a senha do usuário
	 * @return true para sucesso, false para falha
	 * @throws SQLException
	 */
	public boolean Login(String usuario, String senha) throws SQLException {
		if (loginServidor.loginNoServidor(usuario, senha)) {
			Cursor cursor = banco.rawQuery("SELECT * FROM " + BD_TABLE_USUARIOS
					+ " WHERE usuario=? AND senha=?", new String[] { usuario,
					senha });
			if (cursor != null) {
				if (cursor.getCount() > 0) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Método usado para verificar se um nome de usuário já está foi cadastrado.
	 * A operação deve ser realizada somente no servidor, não há sentido em
	 * fazê-la localmente. Entretanto, como não temos um servidor atualmente, a
	 * verificação está sendo efetuada no banco de dados, tendo como base os
	 * usuários já cadastrados localmente.
	 * 
	 * @param usuario
	 *            o nome do usuário
	 * @return true para nome existente, false para nome não encontrado
	 * @throws SQLException
	 */
	public boolean nomeExiste(String usuario) throws SQLException {
		if (loginServidor.pesquisaNomeNoServidor(usuario)) {
			Cursor cursor = banco.rawQuery("SELECT * FROM " + BD_TABLE_USUARIOS
					+ " WHERE usuario=?", new String[] { usuario });
			if (cursor != null) {
				if (cursor.getCount() > 0) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * Método usado para baixar as notificações do servidor para o banco.
	 * Aqui, uma FabricaNotificacoes é instanciada, criando novas notificações,
	 * que são armazenadas no banco e aglomeradas em um ArrayList.
	 * 
	 * @throws Exception
	 */
	public void baixarNotificacoesDoServidor() throws Exception{
		ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();

		new FabricaNotificacoes(loginServidor, 30);

		notificacoes = loginServidor.receberNotificacoesDoServidor();

		for (int i = 0; i < notificacoes.size(); i++) {
			ContentValues initialValues = new ContentValues();
			initialValues
					.put(KEY_NOTIF_TITULO, notificacoes.get(i).getTitulo());
			initialValues.put(KEY_NOTIF_REMETENTE, notificacoes.get(i)
					.getRementente());
			initialValues.put(KEY_NOTIF_CONTEUDO, notificacoes.get(i)
					.getConteudo());
			initialValues.put(KEY_NOTIF_TIPO, notificacoes.get(i).getTipo());
			initialValues.put(KEY_NOTIF_DATA_HORA, notificacoes.get(i)
					.getDataHora());
			initialValues.put(KEY_NOTIF_JA_FOI_LIDO, notificacoes.get(i)
					.getFoiLido());

			banco.insert(BD_TABLE_NOTIFICACOES, null, initialValues);

			String queryId = "SELECT _id FROM "+ BD_TABLE_NOTIFICACOES;
			Cursor cursor = banco.rawQuery(queryId, null);
			int id = -1;
			cursor.moveToLast();			
			id = cursor.getInt(0);
			
			notificacoes.get(i).setId(id);
		}
	}

	/**
	 * Método para selecionar todas as notificações disponíveis ao usuário.
	 * O método leva em conta o tipo de usuário e as disciplinas onde ele está
	 * cadastrado.
	 * 
	 * @param usuario	o nome do usuário
	 * @return			ArrayList de notificações
	 */
	public ArrayList<Notificacao> selectTodasNotificacoes(String usuario) {
		ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
		String[] disciplinasInscritas = getDisciplinasInscritas(usuario);

		String selectQuery = "SELECT * FROM " + BD_TABLE_NOTIFICACOES
				+ " WHERE tipo_notificacao='Público' OR remetente IN ("
				+ criarPlaceholders(disciplinasInscritas.length) + ")";

		Cursor cursor = banco.rawQuery(selectQuery, disciplinasInscritas);

		if (cursor.moveToFirst()) {
			do {
				Notificacao notificacao = new Notificacao(cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5));

				notificacoes.add(notificacao);
			} while (cursor.moveToNext());
		}

		return notificacoes;
	}

	/**
	 * Método para selecionar tudo da tabela de notificações.
	 * 
	 * @param usuario	o nome do usuário
	 * @return			ArrayList de notificações
	 */
	public ArrayList<Notificacao> selectTudo(String usuario) {
		ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
		String query = "SELECT * FROM " + BD_TABLE_NOTIFICACOES;
		Cursor cursor = banco.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				Notificacao notificacao = new Notificacao(cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5));

				notificacoes.add(notificacao);
			} while (cursor.moveToNext());
		}

		return notificacoes;
	}

	/**
	 * Método auxiliar para criar placeholders para queries
	 * 
	 * @param len	tamanho do array de strings
	 * @return		string com placeholders
	 */
	private String criarPlaceholders(int len) {
		if (len < 1) {
			throw new RuntimeException("Sem placeholders");
		} else {
			StringBuilder sb = new StringBuilder(len * 2 - 1);
			sb.append("?");
			for (int i = 1; i < len; i++) {
				sb.append(",?");
			}
			return sb.toString();
		}
	}

	/**
	 * Método usado para dar SELECT na categoria das notificações.
	 * 
	 * @param categoriaNome		nome da categoria
	 * @return					ArrayList de notificações
	 */
	public ArrayList<Notificacao> selectNotificacaoCategoria(
			String categoriaNome) {
		ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
		String query = "SELECT * FROM " + BD_TABLE_NOTIFICACOES
				+ " WHERE remetente=?";

		Cursor cursor = banco.rawQuery(query, new String[] { categoriaNome });

		if (cursor.moveToFirst()) {
			do {
				Notificacao notificacao = new Notificacao(cursor.getString(1),
						cursor.getString(2), cursor.getString(3),
						cursor.getString(4), cursor.getString(5));

				notificacoes.add(notificacao);
			} while (cursor.moveToNext());
		}

		return notificacoes;
	}

	/**
	 * Método para verificar se a tabela de notificações está vazia.
	 * 
	 * @return	true para vazia, false para populada
	 */
	public boolean tableNotificacaoEstaVazia() {
		String query = "SELECT * FROM " + BD_TABLE_NOTIFICACOES;
		Cursor cursor = banco.rawQuery(query, null);

		if (cursor != null) {
			if (cursor.getCount() > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método para pegar as disciplinas onde o usuário está inscrito.
	 * 
	 * @param usuario	nome do usuário
	 * @return			String[] de disciplinas
	 */
	public String[] getDisciplinasInscritas(String usuario) {
		String[] disciplinas = { "" };
		String query = "SELECT disciplinas FROM " + BD_TABLE_USUARIOS
				+ " WHERE usuario=?";
		Cursor cursor = banco.rawQuery(query, new String[] { usuario });

		if (cursor.moveToFirst()) {
			String disc = cursor.getString(0);
			disc = disc.substring(1, (disc.length() - 1));
			disciplinas = disc.split(", ");
		}

		return disciplinas;
	}

	/**
	 * Método para pegar a senha do usuário no banco.
	 * 
	 * @param usuario	nome do usuário
	 * @return			senha
	 */
	public String getSenha(String usuario) {
		String senha = "";
		String query = "SELECT senha FROM " + BD_TABLE_USUARIOS
				+ " WHERE usuario=?";
		Cursor cursor = banco.rawQuery(query, new String[] { usuario });

		if (cursor.moveToFirst()) {
			senha = cursor.getString(0);
		}

		return senha;
	}

	/**
	 * Método para pegar o e-mail do usuário no banco.
	 * 
	 * @param usuario	nome do usuário
	 * @return			e-mail
	 */
	public String getEmail(String usuario) {
		String email = "";
		String query = "SELECT email FROM " + BD_TABLE_USUARIOS
				+ " WHERE usuario=?";
		Cursor cursor = banco.rawQuery(query, new String[] { usuario });

		if (cursor.moveToFirst()) {
			email = cursor.getString(0);
		}

		return email;
	}

	/**
	 * Método para pegar o id do usuário no banco.
	 * 
	 * @param usuario	nome do usuário
	 * @return			id
	 */
	public int getId(String usuario) {
		int id = -1;
		String query = "SELECT _id FROM " + BD_TABLE_USUARIOS
				+ " WHERE usuario=?";
		Cursor cursor = banco.rawQuery(query, new String[] { usuario });

		if (cursor.moveToFirst()) {
			id = cursor.getInt(0);
		}

		return id;
	}

	/**
	 * Método para dar update no status de foiLido da notificação no banco.
	 * 
	 * @param id			id da notificação
	 * @param novoStatus	0 para não lido, 1 para lido
	 */
	public void updateJaFoiLido(int id, int novoStatus) {
		try {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_NOTIF_JA_FOI_LIDO, novoStatus);

			banco.update(BD_TABLE_NOTIFICACOES, initialValues, KEY_NOTIF_ID
					+ " = ?", new String[] { String.valueOf(id) });
		} catch (Exception e) {
			Log.e("Erro no update", "erro:", e);
		}
	}

}