package muralufg.fabrica.inf.ufg.br.ouvidoria;

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

public class AnexoAdapter extends ArrayAdapter<ItemAnexo> {

    static int layout = R.layout.item_anexo;

    static class ViewHolder {
        ImageView iconeAnexo;
        TextView tituloAnexo;
        ImageButton removerAnexo;
    }

    private Activity mContext;
    private List<ItemAnexo> mItens;

    public AnexoAdapter(Activity context, List<ItemAnexo> itens) {
        super(context, layout , itens);
        mContext = context;
        mItens = itens;
    }

    @Override
    public void add(ItemAnexo item) {
        mItens.add(item);
    }

    @Override
    public void remove(ItemAnexo object) {
        super.remove(object);
    }

    public void remove(int position){
        mItens.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            final LayoutInflater inflater = mContext.getLayoutInflater();
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.iconeAnexo = (ImageView) convertView.findViewById(R.id.iconeAnexo);
            viewHolder.tituloAnexo = (TextView) convertView.findViewById(R.id.tituloAnexo);
            viewHolder.removerAnexo = (ImageButton) convertView.findViewById(R.id.removerAnexo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ItemAnexo item = mItens.get(position);

        viewHolder.iconeAnexo.setImageResource(getBackgroundResource(item.getMedia()));
        viewHolder.tituloAnexo.setText(item.getNome());
        viewHolder.removerAnexo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                remove(item);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private int getBackgroundResource(ItemAnexo.Media media){
        if(media == ItemAnexo.Media.IMAGEM){
            return R.drawable.ic_action_content_picture;
        } else  if(media == ItemAnexo.Media.AUDIO){
            return R.drawable.ic_action_audio;
        } else if(media == ItemAnexo.Media.VIDEO){
            return R.drawable.ic_action_video;
        }
        else{
            return R.drawable.ic_action_arquivo;
        }
    }
}
