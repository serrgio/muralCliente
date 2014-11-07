package br.ufg.inf.mobile2014.projetoufg.Activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufg.inf.mobile2014.projetoufg.Notificacao;
import br.ufg.inf.mobile2014.projetoufg.NotificacoesExpandableListAdapter;
import br.ufg.inf.mobile2014.projetoufg.R;
import br.ufg.inf.mobile2014.projetoufg.TituloNotificacoesExpandableList;
import br.ufg.inf.mobile2014.projetoufg.Banco.BDAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity responsável pela exibição da parte principal da aplicação.
 * 
 * @author eric
 */
public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerLista;
	private ActionBarDrawerToggle mBotaoAcionaDrawer;
	private CharSequence mDrawerTitulo;
	private CharSequence mTitulo;
	private String[] mTituloCategorias;
	private BDAdapter bdAdapter = new BDAdapter(this);
	private String nomeUsuario;
	private String[] categoriasNotificacao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		nomeUsuario = "";

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			nomeUsuario = extras.getString("NOME_USUARIO");
		}

		bdAdapter.open();

		boolean tabelaVazia = bdAdapter.tableNotificacaoEstaVazia();

		if (tabelaVazia == true) {
			try {
				bdAdapter.baixarNotificacoesDoServidor();
			} catch (Exception e) {
				Log.e("O erro", "Erro:", e);
			}
		}

		mTitulo = mDrawerTitulo = nomeUsuario;
		String[] catPublicos = getResources().getStringArray(
				R.array.categorias_array);
		String[] catPrivados = bdAdapter.getDisciplinasInscritas(nomeUsuario);

		/* String[]s concatenadas - publico e privado */
		List<String> both = new ArrayList<String>(getResources()
				.getStringArray(R.array.categorias_array).length
				+ bdAdapter.getDisciplinasInscritas(nomeUsuario).length);
		Collections.addAll(both, catPublicos);
		Collections.addAll(both, catPrivados);
		mTituloCategorias = both.toArray(new String[both.size()]);
		categoriasNotificacao = both.toArray(new String[both.size()]);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLista = (ListView) findViewById(R.id.left_drawer);

		// cria uma sombra sobre o conteudo da tela quando o menu abre
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// preenche a lista do drawer com as categorias e seta o clickListener
		mDrawerLista.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mTituloCategorias));
		mDrawerLista.setOnItemClickListener(new DrawerItemClickListener());

		// habilita o icone da ActionBar para agir como acionador do Drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		/*
		 * ActionBarDrawerToggle recebe a Activity, o DrawerLayout, o ícone e as
		 * strings de descrição para open e close, por razões de acessibilidade
		 */
		mBotaoAcionaDrawer = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitulo);
				invalidateOptionsMenu(); 
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitulo);
				invalidateOptionsMenu(); 
			}
		};
		mDrawerLayout.setDrawerListener(mBotaoAcionaDrawer);

		if (savedInstanceState == null) {
			selecionaItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mBotaoAcionaDrawer.onOptionsItemSelected(item)) {
			return true;
		}
		// Controla os botões da Action
		switch (item.getItemId()) {
		case R.id.action_settings:
			mostrarConfiguracoes();
			return true;
		case R.id.action_logoff:
			fazerLogoff();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selecionaItem(position);
		}
	}

	private void selecionaItem(int posicao) {
		// dá update na main, subtituindo pelo fragment
		Fragment fragment = new NotificacaoFragment();
		Bundle args = new Bundle();
		args.putInt(NotificacaoFragment.ARG_NOTIFICACAO_NUMERO, posicao);

		String nomeCategoria = categoriasNotificacao[posicao];
		args.putString(NotificacaoFragment.ARG_NOTIFICACAO_CATEGORIA_NOME,
				nomeCategoria);
		args.putString(NotificacaoFragment.ARG_USUARIO_NOME, nomeUsuario);
		args.putStringArray(
				NotificacaoFragment.ARG_NOTIFICACAO_LISTA_CATEGORIAS,
				categoriasNotificacao);
		fragment.setArguments(args);

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// coloca o titulo no topo da ActionBar
		mDrawerLista.setItemChecked(posicao, true);
		setTitle(mTituloCategorias[posicao]);
		mDrawerLayout.closeDrawer(mDrawerLista);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitulo = title;
		getActionBar().setTitle(mTitulo);
	}

	/**
	 * Se usar ActionBarDrawerToggle, é preciso chamá-lo no
	 * onPostCreate() e no onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sincroniza o estado do toggle depois de um onRestoreInstanceState acontecer.
		mBotaoAcionaDrawer.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Passa qualquer mudança de configuração para o drawerToggle
		mBotaoAcionaDrawer.onConfigurationChanged(newConfig);
	}

	public void mostrarConfiguracoes() {
		Intent it = new Intent(getApplicationContext(), CadastroActivity.class);
		it.putExtra("NOME_USUARIO", nomeUsuario);
		startActivity(it);
	}

	private void fazerLogoff() {
		bdAdapter.close();
		Intent it = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(it);
		finish();
	}

	/**
	 * Fragment que aparece no content_frame. Mostra uma lista de notificações.
	 */
	public static class NotificacaoFragment extends Fragment implements
			OnItemClickListener {
		public static final String ARG_NOTIFICACAO_NUMERO = "notificacaoNumero";
		public static final String ARG_NOTIFICACAO_CATEGORIA_NOME = "nomeCategoria";
		public static final String ARG_USUARIO_NOME = "usuario";
		public static final String ARG_NOTIFICACAO_LISTA_CATEGORIAS = "listaCategorias";
		private ArrayList<Notificacao> notificacoes = new ArrayList<Notificacao>();
		private ExpandableListView listaNotificacoes;
		private SparseArray<TituloNotificacoesExpandableList> titulosNotificacoes = new SparseArray<TituloNotificacoesExpandableList>();
		private NotificacoesExpandableListAdapter adapter;

		public NotificacaoFragment() {
			// Esse construtor vazio é necessário para subclasses Fragment.
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_lista_notificacoes, container, false);
			int numeroCategoria = getArguments().getInt(ARG_NOTIFICACAO_NUMERO);
			String[] listaCategorias = getArguments().getStringArray(
					ARG_NOTIFICACAO_LISTA_CATEGORIAS);
			String tipoNotificacao = listaCategorias[numeroCategoria];
			String catNome = getArguments().getString(
					ARG_NOTIFICACAO_CATEGORIA_NOME);
			String usuarioNome = getArguments().getString(ARG_USUARIO_NOME);

			BDAdapter bdAdapter = new BDAdapter(getActivity());

			bdAdapter.open();

			if (numeroCategoria == 0) {
				notificacoes = bdAdapter.selectTodasNotificacoes(usuarioNome);
			} else {
				notificacoes = bdAdapter.selectNotificacaoCategoria(catNome);
			}

			for (int i = 0; i < notificacoes.size(); i++) {
				Notificacao not = notificacoes.get(i);
				TituloNotificacoesExpandableList tituloNotificacoes = new TituloNotificacoesExpandableList(
						not);
				tituloNotificacoes.children.add(not.getConteudo());
				titulosNotificacoes.append(i, tituloNotificacoes);
			}

			adapter = new NotificacoesExpandableListAdapter(getActivity(),
					titulosNotificacoes);
			listaNotificacoes = (ExpandableListView) rootView
					.findViewById(R.id.listViewExpansivel);
			listaNotificacoes.setAdapter(adapter);

			getActivity().setTitle(tipoNotificacao);

			return rootView;
		}

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long id) {
			Toast.makeText(getActivity().getApplicationContext(),
					((TextView) view).getText(), Toast.LENGTH_SHORT).show();

		}
	}
}