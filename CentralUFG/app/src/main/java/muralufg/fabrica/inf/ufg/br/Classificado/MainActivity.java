package br.ufg.inf.mobile2014.projetoufg;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerLista;
    private ActionBarDrawerToggle mBotaoAcionaDrawer;

    private CharSequence mDrawerTitulo;
    private CharSequence mTitulo;
    private String[] mTituloCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitulo = mDrawerTitulo = "UFG Notificações";
        mTituloCategorias = getResources().getStringArray(R.array.categorias_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLista = (ListView) findViewById(R.id.left_drawer);

        // cria uma sombra sobre o conteudo da tela quando o menu abre
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // preenche a lista do drawer com as categorias e seta o clickListener
        mDrawerLista.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTituloCategorias));
        mDrawerLista.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        // habilita o icone da ActionBar para agir como acionador do Drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mBotaoAcionaDrawer = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitulo);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitulo);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mBotaoAcionaDrawer);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLista);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mBotaoAcionaDrawer.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
//        case R.id.action_websearch:
//            // create intent to perform web search for this planet
//            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//            // catch event that there's no activity to handle intent
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//            }
//            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new NotificacaoFragment();
        Bundle args = new Bundle();
        args.putInt(NotificacaoFragment.ARG_NOTIFICACAO_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerLista.setItemChecked(position, true);
        setTitle(mTituloCategorias[position]);
        mDrawerLayout.closeDrawer(mDrawerLista);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitulo = title;
        getActionBar().setTitle(mTitulo);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mBotaoAcionaDrawer.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mBotaoAcionaDrawer.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class NotificacaoFragment extends Fragment implements OnItemClickListener {
        public static final String ARG_NOTIFICACAO_NUMBER = "notificacao_number";
        private ExpandableListView listaNotificacoes;
        // Usar SparseArray parece ser mais eficiente para mapear integer para objects do que HashMap
        //private SparseArray<GrupoExpandableList> grupos = new SparseArray<GrupoExpandableList>();
        //private ArrayAdapter<String> listAdapter;
        private SparseArray<TituloNotificacoesExpandableList> titulosNotificacoes = new SparseArray<TituloNotificacoesExpandableList>();
        
        NotificacoesExpandableListAdapter adapter;
        
        public NotificacaoFragment() {
            // Empty constructor required for fragment subclasses
        }

//        @Override
//		public void onCreate(Bundle savedInstanceState) {
//          super.onCreate(savedInstanceState);
//          createData();
//          adapter = new NotificacoesExpandableListAdapter(getActivity(), grupos);
//        }
//
//        public void createData() {
//          for (int j = 0; j < 5; j++) {
//            GrupoExpandableList grupo = new GrupoExpandableList("Teste " + j);
//            for (int i = 0; i < 5; i++) {
//              grupo.children.add("Sub Item" + i);
//            }
//            grupos.append(j, grupo);
//          }
//        }
        
        public void criarNotificacoes() {
        	for (int i = 0; i < 5; i++) {
    			String titulo = "Notificação "+i;
    			String texto = "Isso é o texto da notificação.";
    			Notificacao notificacao = new Notificacao("Remetente "+i, titulo, texto);
    			TituloNotificacoesExpandableList tituloNotificacao = new TituloNotificacoesExpandableList(notificacao.getTitulo(), notificacao.getRementente());
    			tituloNotificacao.children.add(notificacao.getMensagem());
    			titulosNotificacoes.append(i, tituloNotificacao);
    		}
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	View rootView = inflater.inflate(R.layout.fragment_lista_notificacoes, container, false);
            int i = getArguments().getInt(ARG_NOTIFICACAO_NUMBER);
            String tipoNotificacao = getResources().getStringArray(R.array.categorias_array)[i];
            
            //List<String> notificacoes = new ArrayList<String>();
            //createData();
            //adapter = new NotificacoesExpandableListAdapter(getActivity(), grupos);
            criarNotificacoes();
            adapter = new NotificacoesExpandableListAdapter(getActivity(), titulosNotificacoes);
            listaNotificacoes = (ExpandableListView) rootView.findViewById(R.id.listViewExpansivel);
            listaNotificacoes.setAdapter(adapter);
            //listaNotificacoes.setAdapter((ListAdapter) adapter);

            getActivity().setTitle(tipoNotificacao);
            return rootView;
        }

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			Toast.makeText(getActivity().getApplicationContext(), ((TextView) view).getText(),
	                  Toast.LENGTH_SHORT).show();
			
		}
    }
}