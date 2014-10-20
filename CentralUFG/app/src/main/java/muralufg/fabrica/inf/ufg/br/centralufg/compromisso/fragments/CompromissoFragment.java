package muralufg.fabrica.inf.ufg.br.centralufg.compromisso.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.frasedodia.services.FraseDoDiaService;
import muralufg.fabrica.inf.ufg.br.centralufg.model.Compromisso;
import muralufg.fabrica.inf.ufg.br.centralufg.model.FraseDoDia;
import muralufg.fabrica.inf.ufg.br.centralufg.util.ServiceCompliant;

public class CompromissoFragment extends Fragment {

    public static final String ARG_DIA = "dia";
    public static final String ARG_MES = "mes";
    public static final String ARG_ANO = "ano";

    private Calendar data = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_compromisso, container, false);
        Bundle args = getArguments();
        data.set(args.getInt(ARG_ANO), args.getInt(ARG_MES), args.getInt(ARG_DIA));


        Compromisso compromisso1 = new Compromisso("Verificar notas da prova TACS",
                "As notas da prova de Técnicas avançadas de construção de software já estão" +
                        "disponíveis no Moodle para consulta.", data);
        Compromisso compromisso2 = new Compromisso("Prova de TACS",
                "A prova de Técnicas avançadas de construção de software será na sala" +
                        "106, no centro de aulas A.", data);
        Compromisso compromisso3 = new Compromisso("Lembrete de aula",
                "Lembrete que haverá aula normalmente hoje.", data);

        Compromisso [] compromissos = {compromisso1, compromisso2, compromisso3};


        ListView listView = (ListView) rootView.findViewById(R.id.listViewCompromisso);
        ArrayAdapter<Compromisso> adapter = new ArrayAdapter<Compromisso>(getActivity(),
                android.R.layout.simple_list_item_1, compromissos);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getActivity().findViewById(R.id.listViewCompromisso);
        ArrayAdapter<Compromisso> adapter = new ArrayAdapter<Compromisso>(getActivity(),
                android.R.layout.simple_list_item_1, compromissos);
        listView.setAdapter(adapter);
    }*/

}
