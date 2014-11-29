package br.ufg.inf.mobile2014.projetoufg.Activities;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufg.inf.mobile2014.projetoufg.R;
import br.ufg.inf.mobile2014.projetoufg.Banco.BDAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

/**
 * Activity de cadastro de usuários. Aqui, é possível cadastrar um usuário novo,
 * ou modificar as configurações de um usuário já existente.
 * 
 * @author eric
 */
public class CadastroActivity extends Activity {

	private EditText etUsuario;
	private EditText etSenha;
	private EditText etSenha_conf;
	private EditText etEmail;
	private CheckBox cbDisciplina1;
	private CheckBox cbDisciplina2;
	private CheckBox cbDisciplina3;
	private CheckBox cbDisciplina4;
	private CheckBox cbDisciplina5;
	private Button btRegistrar;
	private BDAdapter bdAdapter;
	private String nomeUsuarioConfiguracoes = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);

		etUsuario = (EditText) findViewById(R.id.edittext_usuario_nome);
		etSenha = (EditText) findViewById(R.id.edittext_usuario_senha);
		etSenha_conf = (EditText) findViewById(R.id.edittext_usuario_senha_conf);
		etEmail = (EditText) findViewById(R.id.edittext_usuario_email);
		cbDisciplina1 = (CheckBox) findViewById(R.id.checkboxDisciplina1);
		cbDisciplina2 = (CheckBox) findViewById(R.id.checkboxDisciplina2);
		cbDisciplina3 = (CheckBox) findViewById(R.id.checkboxDisciplina3);
		cbDisciplina4 = (CheckBox) findViewById(R.id.checkboxDisciplina4);
		cbDisciplina5 = (CheckBox) findViewById(R.id.checkboxDisciplina5);
		btRegistrar = (Button) findViewById(R.id.button_registrar);
		bdAdapter = new BDAdapter(this);

		bdAdapter.open();

		try {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				nomeUsuarioConfiguracoes = extras.getString("NOME_USUARIO");
				etUsuario.setText(nomeUsuarioConfiguracoes);
				etUsuario.setFocusable(false);

				String senha = bdAdapter.getSenha(nomeUsuarioConfiguracoes);
				etSenha.setText(senha);

				String email = bdAdapter.getEmail(nomeUsuarioConfiguracoes);
				etEmail.setText(email, TextView.BufferType.EDITABLE);

				String[] disciplinas = bdAdapter
						.getDisciplinasInscritas(nomeUsuarioConfiguracoes);
				for (int i = 0; i < disciplinas.length; i++) {
					if (disciplinas[i].equals("Disciplina 1")) {
						cbDisciplina1.setChecked(true);
					}
					if (disciplinas[i].equals("Disciplina 2")) {
						cbDisciplina2.setChecked(true);
					}
					if (disciplinas[i].equals("Disciplina 3")) {
						cbDisciplina3.setChecked(true);
					}
					if (disciplinas[i].equals("Disciplina 4")) {
						cbDisciplina4.setChecked(true);
					}
					if (disciplinas[i].equals("Disciplina 5")) {
						cbDisciplina5.setChecked(true);
					}
				}
			}
		} catch (Exception e) {
			Log.e("O erro", "Erro no extra: ", e);
		}

		btRegistrar.setOnClickListener(new OnClickListener() {

			ArrayList<String> disciplinasSelecionadas = new ArrayList<String>();

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etUsuario.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etSenha.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etSenha_conf.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
				String txtUsuario = etUsuario.getText().toString();
				String txtSenha = etSenha.getText().toString();
				String txtSenhaConf = etSenha_conf.getText().toString();
				String txtEmail = etEmail.getText().toString();
				boolean usuarioOK = false;
				boolean senhaOK = false;
				boolean senhaConfOK = false;
				boolean emailOK = false;
				disciplinasSelecionadas.clear();
				String tipoUsuario;

				if (nomeUsuarioConfiguracoes != null) {
					etUsuario.setText(nomeUsuarioConfiguracoes,
							TextView.BufferType.EDITABLE);
					etUsuario.setEnabled(false);
					usuarioOK = true;
				} else {
					if (etUsuario.length() > 0) {
						try {
							if (bdAdapter.nomeExiste(txtUsuario)) {
								etUsuario.setError("Nome indisponível");
							} else {
								usuarioOK = true;
							}
						} catch (SQLException e) {
							Toast.makeText(CadastroActivity.this,
									"Some problem occurred", Toast.LENGTH_LONG)
									.show();
						}
					} else {
						etUsuario.setError("O campo deve ser preenchido");
					}
				}

				if (etSenha.length() > 0) {
					senhaOK = true;
				} else {
					etSenha.setError("O campo deve ser preenchido");
				}

				if (txtSenhaConf.equals(txtSenha)) {
					senhaConfOK = true;
				} else {
					etSenha_conf.setError("As senhas não coincidem");
				}

				if (etEmail.length() > 0) {
					if (!emailValido(txtEmail)) {
						etEmail.setError("E-mail inválido");
					} else {
						emailOK = true;
					}
				} else {
					etEmail.setError("O campo deve ser preenchido");
				}

				if (cbDisciplina1.isChecked()) {
					disciplinasSelecionadas.add("Disciplina 1");
				}
				if (cbDisciplina2.isChecked()) {
					disciplinasSelecionadas.add("Disciplina 2");
				}
				if (cbDisciplina3.isChecked()) {
					disciplinasSelecionadas.add("Disciplina 3");
				}
				if (cbDisciplina4.isChecked()) {
					disciplinasSelecionadas.add("Disciplina 4");
				}
				if (cbDisciplina5.isChecked()) {
					disciplinasSelecionadas.add("Disciplina 5");
				}

				if (!cbDisciplina1.isChecked() && !cbDisciplina2.isChecked()
						&& !cbDisciplina3.isChecked()
						&& !cbDisciplina4.isChecked()
						&& !cbDisciplina5.isChecked()) {
					tipoUsuario = "publico";
				} else {
					tipoUsuario = "privado";
				}

				if (usuarioOK == true && senhaOK == true && senhaConfOK == true
						&& emailOK == true) {
					if (nomeUsuarioConfiguracoes != null) {
						bdAdapter.updateCadastro(txtUsuario, txtSenha,
								txtEmail, tipoUsuario,
								disciplinasSelecionadas.toString());
						
						Intent it = new Intent(getApplicationContext(),
								MainActivity.class);
						it.putExtra("NOME_USUARIO", nomeUsuarioConfiguracoes);
						startActivity(it);
						finish();
					} else {
						bdAdapter.registrar(txtUsuario, txtSenha, txtEmail,
								tipoUsuario, disciplinasSelecionadas.toString());
						
						Intent it = new Intent(getApplicationContext(),
								LoginActivity.class);
						startActivity(it);
						finish();
					}
				}
			}
		});

	}

	/**
	 * Método para verificar se o endereço de e-mail é válido
	 * 
	 * @param email		endereço de e-mail
	 * @return			true para válido, false para inválido
	 */
	public boolean emailValido(String email) {
		String EMAIL_PATTERN = "[a-zA-Z0-9+._%-+]{1,256}" + "@"
				+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
				+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}