package cifrasong.cifra.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import cifrasong.R;
import cifrasong.cifra.dominio.Cifra;
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.gui.MenuActivity;

/**
 * Created by Uehara on 01/12/2014.
 */
public class PesquisaArtistaAct extends android.support.v7.app.AppCompatActivity {

    final CifraService negocio = new CifraService(PesquisaArtistaAct.this);

    Toolbar toolbar;

    static List<Cifra> cifras;
    ArrayAdapter cifrasAdapter;
    ListView lista;

    public void onBackPressed() {
        cifras = null;
        Intent intent = new Intent();
        intent.setClass(PesquisaArtistaAct.this, PesquisaAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cifras_artistas);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cifras = null;
                Intent i = new Intent(PesquisaArtistaAct.this, PesquisaAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        final TextView texto = (TextView) findViewById(R.id.texto);
        final Button button = (Button) findViewById(R.id.botao);

        cifrasAdapter = new CifrasAdapter(this);

        lista = (ListView) findViewById(R.id.minhasCifras);
        lista.setAdapter(cifrasAdapter);
        lista.setClickable(true);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cifra cifraSelecionada = (Cifra) lista.getItemAtPosition(i);
                texto.setText("Aguarde a busca.");
                PesquisaCifraAsync pesquisa = new PesquisaCifraAsync(PesquisaArtistaAct.this, progress, texto, negocio.montaLink(cifraSelecionada.getArtista() + "/" + cifraSelecionada.getNome()), button);
                ExibeCifraPesquisaAct.cifraNome = cifraSelecionada.getNome();
                ExibeCifraPesquisaAct.cifraArtista = cifraSelecionada.getArtista();
                pesquisa.execute();
            }
        });
    }


    private class CifrasAdapter extends ArrayAdapter<Cifra> {
        private Context ctx;

        public CifrasAdapter(Context ctx) {
            super(ctx, R.layout.activity_lista_cifras, cifras);
            this.ctx = ctx;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null) {
                view = getLayoutInflater().from(this.ctx).inflate(R.layout.itens_list_view, null);
            }

            Cifra cifra = cifras.get(position);

            TextView nomeCifra = (TextView) view.findViewById(R.id.musicaCifra);
            TextView artistaCifra = (TextView) view.findViewById(R.id.artistaCifra);
            nomeCifra.setText(cifra.getNome());
            artistaCifra.setText(cifra.getArtista());
            return view;
        }
    }
} 	