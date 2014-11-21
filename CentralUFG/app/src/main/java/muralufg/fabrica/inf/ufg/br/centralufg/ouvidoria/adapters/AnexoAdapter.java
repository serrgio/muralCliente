package muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.ouvidoria.models.OuvidoriaItemAnexo;

/**
 * Adapter para a lista dos anexos que devem ser enviados para ouvidoria
 */
public class AnexoAdapter extends ArrayAdapter<OuvidoriaItemAnexo> {

    static int layout = R.layout.adapter_ouvidoria_item_anexo;

    /**
     * Padrão para acessar cada elemento que está sendo visualizado na lista
     */
    static class ViewHolder {
        ImageView iconeAnexo;
        TextView tituloAnexo;
        ImageButton removerAnexo;
    }

    /**
     * Contexto da activity
     */
    private Activity mContext;
    /**
     * Lista contendo os item anexos da ouvidoria
     */
    private List<OuvidoriaItemAnexo> mItens;

    public AnexoAdapter(Activity context, List<OuvidoriaItemAnexo> itens) {
        super(context, layout, itens);
        mContext = context;
        mItens = itens;
    }

    @Override
    public void add(OuvidoriaItemAnexo item) {
        mItens.add(item);
    }

    @Override
    public void remove(OuvidoriaItemAnexo object) {
        mItens.remove(object);
    }

    /**
     * Remove o item da lista a partir da posição
     *
     * @param position
     */
    public void remove(int position) {
        mItens.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            final LayoutInflater inflater = mContext.getLayoutInflater();
            view = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.iconeAnexo = (ImageView) view.findViewById(R.id.iconeAnexo);
            viewHolder.tituloAnexo = (TextView) view.findViewById(R.id.tituloAnexo);
            viewHolder.removerAnexo = (ImageButton) view.findViewById(R.id.removerAnexo);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final OuvidoriaItemAnexo item = mItens.get(position);

        viewHolder.iconeAnexo.setImageResource(getBackgroundResource(item.getMedia()));
        viewHolder.tituloAnexo.setText(item.getNome());
        viewHolder.removerAnexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remover o anexo da lista
                remove(item);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**
     * Selecionar a imagem que está no assets para representar o tipo de arquivo é o anexo
     *
     * @param media
     * @return o icone do anexo
     */
    private int getBackgroundResource(OuvidoriaItemAnexo.Media media) {
        if (media == OuvidoriaItemAnexo.Media.IMAGEM) {
            return R.drawable.ic_action_content_picture;
        } else if (media == OuvidoriaItemAnexo.Media.AUDIO) {
            return R.drawable.ic_action_audio;
        } else if (media == OuvidoriaItemAnexo.Media.VIDEO) {
            return R.drawable.ic_action_video;
        } else {
            return R.drawable.ic_action_arquivo;
        }
    }
}
