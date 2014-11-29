package muralufg.fabrica.inf.ufg.br.centralufg.eventos;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EventosActivity extends Activity {

    ListView listView;
    // TODO colocar um método que puxa as categorias, pois elas pode mudar ou podem não ter subitens na categoria
    // TODO as categorias deverão ser puxadas e colocadas no arrey em ordem alfabética
    String[] tiposDeCategorias = {"Apresentação", "Congressos", "Defesas", "Palestras", "Provas",
            "Reuniões", "Seminários", "Shows" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoriasdeeventos);
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> array = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, tiposDeCategorias);
        listView.setAdapter(array);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
