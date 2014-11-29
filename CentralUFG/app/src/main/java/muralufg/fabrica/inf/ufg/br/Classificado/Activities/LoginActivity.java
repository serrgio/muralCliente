package br.ufg.inf.mobile2014.projetoufg.Activities;

import br.ufg.inf.mobile2014.projetoufg.R;
import br.ufg.inf.mobile2014.projetoufg.Banco.BDAdapter;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity responsável pelo login do usuário.
 * 
 * @author eric
 */
public class LoginActivity extends Activity {
	BDAdapter dbAdapter;
	EditText txtUserName;
	EditText txtPassword;
	Button btnLogin;
	Button btnRegister;
	private View mLoginFormView;
	private View mLoginStatusView;
	private UserLoginTask mAuthTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		txtUserName = (EditText) findViewById(R.id.et_user);
		txtPassword = (EditText) findViewById(R.id.et_pw);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnRegister = (Button) findViewById(R.id.btn_reg);

		dbAdapter = new BDAdapter(this);
		dbAdapter.open();

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
				String username = txtUserName.getText().toString();
				String password = txtPassword.getText().toString();
				if (username.length() > 0 && password.length() > 0) {
					try {

						if (dbAdapter.Login(username, password)) {
							showProgress(true);
							mAuthTask = new UserLoginTask();
							mAuthTask.execute((Void) null);
						} else {
							Toast.makeText(LoginActivity.this,
									"Invalid username or password",
									Toast.LENGTH_LONG).show();
						}

					} catch (Exception e) {
						Log.e("O Erro", "A exceção", e);
						Toast.makeText(LoginActivity.this,
								"Some problem occurred", Toast.LENGTH_LONG)
								.show();

					}
				} else {
					Toast.makeText(LoginActivity.this,
							"Username or Password is empty", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getBaseContext(),
						CadastroActivity.class));
			}
		});
	}

	/**
	 * Método para chamar a barra (círculo, na verdade) de progresso. Esse
	 * método foi gerado pela prórpia IDE.
	 * 
	 * @param show
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		mLoginStatusView = new View(getBaseContext());
		mLoginFormView = new View(getBaseContext());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Classe responsável pelas operações de carregamento da próxima tela.
	 * AsyncTask é usada.
	 * 
	 * @author eric
	 * 
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				// Simula o acesso de rede
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Intent it = new Intent(getApplicationContext(),
						MainActivity.class);
				it.putExtra("NOME_USUARIO", txtUserName.getText().toString());
				startActivity(it);
				finish();
			} else {
				txtPassword
						.setError(getString(R.string.error_incorrect_password));
				txtPassword.requestFocus();
			}
		}
	}
}