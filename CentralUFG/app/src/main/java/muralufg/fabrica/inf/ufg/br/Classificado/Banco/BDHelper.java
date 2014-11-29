package br.ufg.inf.mobile2014.projetoufg.Banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe auxiliar do banco de dados. Aqui, dados como nome e versão do banco de
 * dados são armazenados, além dos comandos responsáveis por criar a tabela de
 * usuários e administrar o versionamento.
 * 
 * @author eric
 */
public class BDHelper extends SQLiteOpenHelper {

	private static final String BD_NOME = "ufg_db";
	private static final int BD_VERSION = 1;
	private static final String BD_CREATE_USUARIOS = "CREATE TABLE usuarios (_id integer primary key autoincrement, usuario text not null, senha text not null, email text not null, tipo_usuario text not null, disciplinas text not null);";
	private static final String BD_CREATE_NOTIFICACOES = "CREATE TABLE notificacoes (_id integer primary key autoincrement, titulo text not null, remetente text not null, conteudo text not null, tipo_notificacao text not null, data_hora datetime default current_timestamp, ja_foi_lido integer not null);";

	public BDHelper(Context context) {
		super(context, BD_NOME, null, BD_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase bd) {
		bd.execSQL(BD_CREATE_USUARIOS);
		bd.execSQL(BD_CREATE_NOTIFICACOES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
		bd.execSQL("DROP TABLE IF EXISTS usuarios");
		bd.execSQL("DROP TABLE IF EXISTS notificacoes");

		onCreate(bd);
	}

}