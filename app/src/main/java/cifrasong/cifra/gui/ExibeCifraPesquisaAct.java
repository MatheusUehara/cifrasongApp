package cifrasong.cifra.gui;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cifrasong.R;
import cifrasong.cifra.dominio.Cifra;
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.gui.MenuActivity;


/**
 * Created by Uehara on 20/10/2014.
 */
public class ExibeCifraPesquisaAct extends android.support.v7.app.AppCompatActivity {


    public void onBackPressed() {
        finish();
    }

    final CifraService negocio = new CifraService(ExibeCifraPesquisaAct.this);


    Toolbar toolbar;
    public static String cifraMusica = null;
    public static String cifraArtista = null;
    public static String cifraNome = null;

    public static String shareWhats = null;
    public static int tamanhoRolagem;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_cifra);
        setTitle(cifraArtista);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView cifra = (TextView) findViewById(R.id.letraCifra);

        Resources res = this.getResources();

        if (cifraMusica.contains("<b>")) {
            cifraMusica = cifraMusica.replaceAll("<b>", "<FONT COLOR=#00BFFF>");
            cifraMusica = cifraMusica.replaceAll("</b>", "</FONT>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp");
        }

        String conteudo = String.format(res.getString(R.string.cifra), cifraArtista, cifraNome, cifraMusica);
        cifra.setText(Html.fromHtml(conteudo));
        shareWhats = Html.fromHtml(conteudo).toString();
        cifra.measure(0, 0);
        tamanhoRolagem = cifra.getMeasuredHeight();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exibe_cifra_pesquisa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.salvar) {
            Cifra cifraA = new Cifra();
            cifraA.setNome(cifraNome);
            cifraA.setArtista(cifraArtista);
            cifraA.setConteudo(cifraMusica);
            try {
                negocio.adicionarCifra(cifraA);
                Toast.makeText(ExibeCifraPesquisaAct.this, "Cifra Adicionada com sucesso.", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(ExibeCifraPesquisaAct.this, "Falha ao tentar adicionar cifra.", Toast.LENGTH_SHORT).show();
            }
        }

        if (id == R.id.share) {
            try {
                Intent oShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                oShareIntent.setType("text/plain");
                oShareIntent.putExtra(Intent.EXTRA_TEXT, shareWhats + "Compartilhado via CifraSong");
                startActivity(Intent.createChooser(oShareIntent, "Compartilhar via:"));
            } catch (Exception e) {
                Toast.makeText(this, "Ocorreu uma falha no compartilhamento..", Toast.LENGTH_SHORT).show();
            }
        }
        /*
        if (id == R.id.rolar){
            ScrollView sv = (ScrollView)findViewById(R.id.scrollView3);
            scrollRight(sv);
        }
        */
        return super.onOptionsItemSelected(item);
    }

    public void scrollRight(final ScrollView h) {
        new CountDownTimer(tamanhoRolagem, 20) {
            public void onTick(long millisUntilFinished) {
                h.scrollTo(0, (int) (tamanhoRolagem - millisUntilFinished));
            }

            public void onFinish() {
            }
        }.start();
    }
}