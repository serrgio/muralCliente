package muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.fragments;

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
import muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.models.OuvidoriaItemAnexo;
import muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.adapters.AnexoAdapter;

public class OuvidoriaFragment extends Fragment {

    public static final int SELECT_IMAGEM = 100;
    public static final int SELECT_AUDIO = 200;
    public static final int SELECT_VIDEO = 300;

    /**
     * Todos os tipos de arquivos de imagem
     */
    public static final String IMAGEM_TYPE = "image/*";
    /**
     * Todos os tipos de arquivos de audio
     */
    public static final String AUDIO_TYPE = "audio/*";
    /**
     * Todos os tipos de arquivos de video
     */
    public static final String VIDEO_TYPE = "video/*";

    private TextView mOuvidoriaTitulo;
    private TextView mOuvidoriaDescricao;
    private ListView mOuvidoriaListaAnexos;
    private ArrayAdapter<OuvidoriaItemAnexo> mAdapaterAnexos;

    /**
     * Construtor default
     */
    public OuvidoriaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Carrega o layout
        View rootView = inflater.inflate(R.layout.fragment_ouvidoria, container, false);

        // Seta os elementos do layout
        mOuvidoriaTitulo = (TextView) rootView.findViewById(R.id.ouvidoriaTitulo);
        mOuvidoriaDescricao = (TextView) rootView.findViewById(R.id.ouvidoriaDescricao);
        mOuvidoriaListaAnexos = (ListView) rootView.findViewById(R.id.ouvidoriaListaAnexos);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicia o adpter vazio para que os anexos  sejam inseridos dinamicamente
        // quando selecionados pelo usuário
        mAdapaterAnexos = new AnexoAdapter(getActivity(), new ArrayList<OuvidoriaItemAnexo>());
        mOuvidoriaListaAnexos.setAdapter(mAdapaterAnexos);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflar o menu da ouvidoria na tela
        inflater.inflate(R.menu.ouvidoria, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
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
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Enviar mensagem
     */
    private void enviarMensagem() {
        showMenssage("Enviando...");
    }

    /**
     * Abrir seletor de arquivo
     *
     * @param type de arquivo deve visualizar
     */
    private void chooserArquivo(String type) {
        Intent chooseIntent = new Intent(Intent.ACTION_PICK);
        chooseIntent.setType(type);
        chooseIntent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(chooseIntent, SELECT_IMAGEM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            final OuvidoriaItemAnexo ouvidoriaItemAnexo;
            switch (requestCode) {
                case SELECT_IMAGEM:
                    ouvidoriaItemAnexo = new OuvidoriaItemAnexo(getFileName(data.getData()), getFileName(data.getData()), OuvidoriaItemAnexo.Media.IMAGEM);
                    addItemLista(ouvidoriaItemAnexo);
                    break;
                case SELECT_AUDIO:
                    ouvidoriaItemAnexo = new OuvidoriaItemAnexo(getFileName(data.getData()), data.getData().getPath(), OuvidoriaItemAnexo.Media.AUDIO);
                    addItemLista(ouvidoriaItemAnexo);
                    break;
                case SELECT_VIDEO:
                    ouvidoriaItemAnexo = new OuvidoriaItemAnexo(getFileName(data.getData()), data.getData().getPath(), OuvidoriaItemAnexo.Media.VIDEO);
                    addItemLista(ouvidoriaItemAnexo);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Capturar o nome do arquivo selecionado
     *
     * @param uri
     * @return nome do arquivo
     */
    public String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
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

    /**
     * Adicionar os anexos selecionados na lista para visualização
     *
     * @param ouvidoriaItemAnexo
     */
    private void addItemLista(OuvidoriaItemAnexo ouvidoriaItemAnexo) {
        mAdapaterAnexos.add(ouvidoriaItemAnexo);
        mAdapaterAnexos.notifyDataSetChanged();
    }

    /**
     * Mostrar o Toast para o usuário
     *
     * @param mensagem
     */
    private void showMenssage(String mensagem) {
        Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();
    }


}