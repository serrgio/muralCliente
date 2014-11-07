package br.ufg.inf.mobile2014.projetoufg;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Classe que serve de Adapter para a ExpandableList de notificações.
 * Algumas partes do código dessa classe são uma compilação de uma série
 * de postagens de fórums como o stackoverflow.com.
 * 
 * @author eric
 */
public class NotificacoesExpandableListAdapter extends BaseExpandableListAdapter {
	private final SparseArray<TituloNotificacoesExpandableList> titulosNotificacoes;
	public LayoutInflater inflater;
	public Activity activity;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param act					Activity
	 * @param titulosNotificacoes	array de titulos de notificações 
	 */
	public NotificacoesExpandableListAdapter(Activity act, SparseArray<TituloNotificacoesExpandableList> titulosNotificacoes) {
		activity = act;
		this.titulosNotificacoes = titulosNotificacoes;
		inflater = act.getLayoutInflater();
	}
	
	@Override
	public Object getChild(int grupoPosicao, int childPosicao) {
		return titulosNotificacoes.get(grupoPosicao).children.get(childPosicao);
	}

	@Override
	public long getChildId(int grupoPosicao, int childPosicao) {
		return 0;
	}

	@Override
	public View getChildView(int grupoPosicao, final int childPosicao, boolean isLastChild, View convertView,
			ViewGroup parent) {
		final String children = (String) getChild(grupoPosicao, childPosicao);
	    TextView texto = null;
	    if (convertView == null) {
	      convertView = inflater.inflate(R.layout.listrow_detalhes, null);
	    }
	    texto = (TextView) convertView.findViewById(R.id.textViewDetalhes);
	    texto.setText(children);
	    convertView.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {	        
	    	  Toast.makeText(activity, children, Toast.LENGTH_SHORT).show();
	      }
	    });
	    return convertView;
	}

	@Override
	public int getChildrenCount(int grupoPosicao) {
		return titulosNotificacoes.get(grupoPosicao).children.size();
	}

	@Override
	public Object getGroup(int grupoPosicao) {
		return titulosNotificacoes.get(grupoPosicao);
	}

	@Override
	public int getGroupCount() {
		return titulosNotificacoes.size();
	}

	@Override
	public long getGroupId(int grupoPosicao) {
		return 0;
	}

	@Override
	public View getGroupView(int grupoPosicao, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listrow_notificacoes_titulo, null);
		}
		TituloNotificacoesExpandableList titulo = (TituloNotificacoesExpandableList) getGroup(grupoPosicao);
		TextView textViewTitulo = (TextView) convertView.findViewById(R.id.textViewTituloNotificacoes);
		TextView textViewRemetente = (TextView) convertView.findViewById(R.id.textViewRemetenteNotificacoes);
		TextView textViewData = (TextView) convertView.findViewById(R.id.textViewDataNotificacoes);
		textViewData.setText(titulo.getNotificacao().getDataHora());
		textViewRemetente.setText(titulo.getNotificacao().getRementente());
		textViewTitulo.setText(titulo.getNotificacao().getTitulo());
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int grupoPosicao, int childPosicao) {
		return false;
	}
	
	@Override
	public void onGroupCollapsed(int grupoPosicao) {
		super.onGroupCollapsed(grupoPosicao);
	}
	
	@Override
	public void onGroupExpanded(int grupoPosicao) {
	    super.onGroupExpanded(grupoPosicao);
	}

}
