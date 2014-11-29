package muralufg.fabrica.inf.ufg.br.centralufg.locais.fragments;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.frasedodia.services.FraseDoDiaService;
import muralufg.fabrica.inf.ufg.br.centralufg.locais.adapters.LocaisAdapter;
import muralufg.fabrica.inf.ufg.br.centralufg.locais.services.LocaisService;
import muralufg.fabrica.inf.ufg.br.centralufg.model.FraseDoDia;
import muralufg.fabrica.inf.ufg.br.centralufg.model.Locais;
import muralufg.fabrica.inf.ufg.br.centralufg.util.ServiceCompliant;

public class LocaisFragment extends ListFragment implements ServiceCompliant {

    private SmoothProgressBar mPocketBar;
    LocaisAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locais, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        executar();
        //FraseDoDiaService service = new FraseDoDiaService(this);
        //service.execute();
    }

    private void executar() {
        List<Locais> listaLocais = null;

        Locais locais = new Locais();
        locais.setId(1);
        locais.setNome("nome 1");
        locais.setEndereco("endereco 1");

        Locais locais2 = new Locais();
        locais.setId(2);
        locais.setNome("nome 2");
        locais.setEndereco("endereco 2");

        listaLocais.add(locais);
        listaLocais.add(locais2);

        adapter = new LocaisAdapter(getContextActivity(), listaLocais);
        setListAdapter(adapter);
    }

    @Override
    public void handleError(String error) {
        Crouton.makeText(this.getActivity(), error, Style.ALERT).show();
    }

    @Override
    public void readObject(Object object) {
        List<Locais> listaLocais = null;

        Locais locais = new Locais();
        locais.setId(1);
        locais.setNome("nome 1");
        locais.setEndereco("endereco 1");

        Locais locais2 = new Locais();
        locais.setId(2);
        locais.setNome("nome 2");
        locais.setEndereco("endereco 2");


        /*FraseDoDia frase = (FraseDoDia) object;

        TextView labelFrase = (TextView) getView().findViewById(R.id.quote);
        labelFrase.setText(frase.getFrase());

        TextView labelAutor = (TextView) getView().findViewById(R.id.author);
        labelAutor.setText(frase.getAutor());*/

        adapter = new LocaisAdapter(getContextActivity(), listaLocais);
        setListAdapter(adapter);
    }

    public Activity getContextActivity(){
        return this.getActivity();
    }
}
