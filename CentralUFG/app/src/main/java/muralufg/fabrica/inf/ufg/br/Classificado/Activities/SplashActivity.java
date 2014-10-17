package br.ufg.inf.mobile2014.projetoufg.Activities;

import br.ufg.inf.mobile2014.projetoufg.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Activity responsável pela tela de Splash.
 * 
 * @author eric
 */
public class SplashActivity extends Activity implements Runnable {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler h = new Handler();
		h.postDelayed(this, 3000);
	}

	@Override
	public void run() {
		startActivity(new Intent(this, LoginActivity.class));
		finish();	
	}

}
