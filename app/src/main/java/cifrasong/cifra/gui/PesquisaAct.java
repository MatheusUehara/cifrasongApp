package cifrasong.cifra.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;

import cifrasong.R;
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.gui.MenuActivity;


public class PesquisaAct extends android.support.v7.app.AppCompatActivity {
    final CifraService negocio = new CifraService(PesquisaAct.this);

    Toolbar toolbar;

    static PesquisaCifraAsync pesquisa;
    static PesquisaArtistaAsync pesquisaArtista;

    public void onBackPressed() {
        Intent intent = new Intent(PesquisaAct.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pesquisa);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PesquisaAct.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListaArtistas.artistas);

        final Button pesquisar = (Button) findViewById(R.id.pesquisar);
        final EditText nomeMusica = (EditText) findViewById(R.id.nomeMusica);
        final AutoCompleteTextView nomeArtista = (AutoCompleteTextView) findViewById(R.id.nomeArtista);

        final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        final TextView texto = (TextView) findViewById(R.id.texto);

        nomeArtista.setAdapter(adapter);


        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sufixo = tiraChar(nomeArtista.getText().toString()) + "/" + tiraChar(nomeMusica.getText().toString());
                for (int i=0; i < ListaArtistas.listaExibicao.length; i++) {
                    if (nomeArtista.getText().toString().matches(ListaArtistas.listaExibicao[i].toLowerCase())) {
                        ExibeCifraPesquisaAct.cifraArtista = ListaArtistas.listaExibicao[i];
                    }
                }

                pesquisa = new PesquisaCifraAsync(PesquisaAct.this, progress, texto, negocio.montaLink(sufixo), pesquisar);

                pesquisaArtista = new PesquisaArtistaAsync(PesquisaAct.this, progress, texto, negocio.montaLink(nomeArtista.getText().toString()), pesquisar, nomeArtista.getText().toString());

                String artista = nomeArtista.getText().toString();
                String musica = nomeMusica.getText().toString();

                try {
                    if (musica.length() == 0 && artista.length() != 0) {
                        progress.setVisibility(View.VISIBLE);
                        pesquisaArtista.execute();
                        pesquisar.setClickable(false);
                        Toast.makeText(PesquisaAct.this, "Aguarde a nossa Busca.", Toast.LENGTH_SHORT).show();
                    } else if (musica.length() == 0) {
                        Toast.makeText(PesquisaAct.this, "Digite um musica para a busca.", Toast.LENGTH_SHORT).show();
                    } else if (artista.length() == 0) {
                        Toast.makeText(PesquisaAct.this, "Digite um artista para a busca.", Toast.LENGTH_SHORT).show();
                    } else {
                        pesquisa.execute();
                        pesquisar.setClickable(false);
                        progress.setVisibility(View.VISIBLE);
                        Toast.makeText(PesquisaAct.this, "Aguarde a nossa Busca.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PesquisaAct.this, "Verifique sua conexão com a Internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String tiraChar(String input) {
        String text;
        text = Normalizer.normalize(input, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        text = text.toLowerCase();
        return text;
    }
}