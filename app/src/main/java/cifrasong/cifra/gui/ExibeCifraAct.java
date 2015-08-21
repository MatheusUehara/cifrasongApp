package cifrasong.cifra.gui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
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
    final CifraService negocio = new CifraService(this);

    public void onBackPressed(){
        Intent intent = new Intent(ExibeCifraAct.this,MenuActivity.class);
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


        final TextView cifra = (TextView)findViewById(R.id.letraCifra);

        Resources res = this.getResources();
        String conteudo = String.format(res.getString(R.string.cifra), Session.getCifraSelecionada().getNome(), " ", Session.getCifraSelecionada().getConteudo());
        cifra.setText(Html.fromHtml(conteudo));
        shareWhats = Html.fromHtml(conteudo).toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exibe_cifra_salva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.excluir) {
            try {
                negocio.deletarCifra();
                Toast.makeText(ExibeCifraAct.this, "Cifra deletada com sucesso.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(ExibeCifraAct.this,MenuActivity.class);
                startActivity(i);
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

        if (id == R.id.share){
            try{
                Intent oShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                oShareIntent.setType("text/plain");
                oShareIntent.putExtra(Intent.EXTRA_TEXT, shareWhats + "Compartilhado via CifraSong");
                startActivity(oShareIntent);
            }catch(Exception e){
                Toast.makeText(ExibeCifraAct.this, "Ocorreu uma falha no compartilhamento.", Toast.LENGTH_SHORT).show();
            }
        }
        if (id == R.id.rolar){


            ScrollView sv = (ScrollView)findViewById(R.id.scrollView3);
            scrollRight(sv);
        }
        return super.onOptionsItemSelected(item);
    }

    public void scrollRight(final ScrollView h){
        new CountDownTimer(2000, 20) {

            public void onTick(long millisUntilFinished) {
                h.scrollTo(0,(int) (2000 - millisUntilFinished));
            }

            public void onFinish() {

            }
        }.start(); }
}