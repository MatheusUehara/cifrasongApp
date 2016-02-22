package cifrasong.cifra.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.gui.MenuActivity;


/**
 * Created by Uehara on 20/10/2014.
 */
public class ExibeCifraAct extends android.support.v7.app.AppCompatActivity {

    Toolbar toolbar;
    public static String shareWhats = null;
    public static int tamanhoRolagem;
    final CifraService negocio = new CifraService(this);

    public void onBackPressed() {
        Intent intent = new Intent(ExibeCifraAct.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_cifra);

        setTitle(Session.getCifraSelecionada().getArtista());

            // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExibeCifraAct.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        final TextView cifra = (TextView) findViewById(R.id.letraCifra);

        Resources res = this.getResources();
        String conteudo = String.format(res.getString(R.string.cifra), Session.getCifraSelecionada().getNome(), " ", Session.getCifraSelecionada().getConteudo());
        cifra.setText(Html.fromHtml(conteudo));
        shareWhats = Html.fromHtml(conteudo).toString();
        cifra.measure(0, 0);
        tamanhoRolagem = cifra.getMeasuredHeight();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exibe_cifra_salva, menu);
        return true;
    }

    /**
     * Builder do AlertDialog da exclusao de cifra
     */

    //atributo da classe.
    private AlertDialog alerta;

    private void ExcluirDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Cifra");
        builder.setMessage("Tem certeza que deseja excluir esta cifra?");

        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                negocio.deletarCifra();
                Toast.makeText(ExibeCifraAct.this, "Cifra deletada com sucesso.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(ExibeCifraAct.this, MenuActivity.class);
                startActivity(i);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(ExibeCifraAct.this, "Cifra não deletada", Toast.LENGTH_SHORT).show();
            }
        });

        alerta = builder.create();
        alerta.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.excluir) {
            try {
                ExcluirDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (id == R.id.favoritar) {
            try {
                if (negocio.favoritarCifra(Session.getCifraSelecionada())) {
                    Intent intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (id == R.id.share) {
            try {
                Intent oShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                oShareIntent.setType("text/plain");
                oShareIntent.putExtra(Intent.EXTRA_TEXT, shareWhats + "Compartilhado via CifraSong");
                startActivity(Intent.createChooser(oShareIntent, "Compartilhar via:"));
            } catch (Exception e) {
                Toast.makeText(ExibeCifraAct.this, "Ocorreu uma falha no compartilhamento.", Toast.LENGTH_SHORT).show();
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