package muralufg.fabrica.inf.ufg.br.ouvidoria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

public class OuvidoriaFragment extends Fragment {

    public static final int SELECT_IMAGEM = 100;
    public static final int SELECT_AUDIO = 200;
    public static final int SELECT_VIDEO = 300;

    public static final String IMAGEM_TYPE = "image/*";
    public static final String AUDIO_TYPE = "audio/*";
    public static final String VIDEO_TYPE = "video/*";

    private TextView tituloText;
    private TextView descricaoText;
    private ListView anexosListView;
    private ArrayAdapter<ItemAnexo> adapterAnexos;

    public OuvidoriaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_ouvidoria, container, false);

        tituloText = (TextView) rootView.findViewById(R.id.tituloText);
        descricaoText = (TextView) rootView.findViewById(R.id.descricaoText);
        anexosListView = (ListView) rootView.findViewById(R.id.anexosGridView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapterAnexos = new AnexoAdapter(getActivity(), new ArrayList<ItemAnexo>());
        anexosListView.setAdapter(adapterAnexos);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ouvidoria, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_enviar:
                enviarMensagem();
                break;
            case R.id.action_anexar_imagem:
                chooserArquivo(IMAGEM_TYPE);
                break;
            case R.id.action_anexar_audio:
                chooserArquivo(AUDIO_TYPE);
                break;
            case R.id.action_anexar_video:
                chooserArquivo(VIDEO_TYPE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Enviar mensagem
     */
    private void enviarMensagem(){
        showMenssage("Enviando...");
    }

    /**
     * Abrir seletor de arquivo
     * @param type de arquivo deve visualizar
     */
    private void chooserArquivo(String type){
        Intent chooseIntent = new Intent(Intent.ACTION_PICK);
        chooseIntent.setType(type);
        chooseIntent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(chooseIntent, SELECT_IMAGEM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            final ItemAnexo itemAnexo;
            switch (requestCode) {
                case SELECT_IMAGEM:
                    itemAnexo = new ItemAnexo(getFileName(data.getData()), getFileName(data.getData()), ItemAnexo.Media.IMAGEM);
                    addItemLista(itemAnexo);
                    break;
                case SELECT_AUDIO:
                    itemAnexo = new ItemAnexo(getFileName(data.getData()), data.getData().getPath(), ItemAnexo.Media.AUDIO);
                    addItemLista(itemAnexo);
                    break;
                case SELECT_VIDEO:
                    itemAnexo = new ItemAnexo(getFileName(data.getData()), data.getData().getPath(), ItemAnexo.Media.VIDEO);
                    addItemLista(itemAnexo);
                    break;
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void addItemLista(ItemAnexo itemAnexo ){
        adapterAnexos.add(itemAnexo);
        adapterAnexos.notifyDataSetChanged();
        showMenssage(itemAnexo.getUri());
    }

    private void showMenssage(String mensagem){
        Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();
    }


}