package cifrasong.cifra.gui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Uehara on 30/11/2014.
 */

public class PesquisaCifraAsync extends AsyncTask <Void, Object, List> {

    private String endereco = "";
    private ProgressBar progressBar;
    private TextView texto;
    private int total = 0;
    private static int PROGRESSO = 1;
    private Button exibir;
    private Context context;

    public PesquisaCifraAsync(Context context, ProgressBar progressBar, TextView texto ,String endereco , Button exibir) {
        this.progressBar = progressBar;
        this.texto = texto;
        this.endereco = endereco;
        this.exibir = exibir;
        this.context = context;
    }

    public PesquisaCifraAsync(Context context,String endereco) {
        this.endereco = endereco;
    }

    @Override
    protected void onPreExecute() {
        texto.setText("0%");
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        List<String> out = new ArrayList<String>();
        try {
            URL url = new URL(this.endereco);
            URLConnection c = url.openConnection();
            c.setConnectTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String inputLine;
            boolean pode = false;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("Tom:")) {
                    pode = true;
                } else if (inputLine.contains("</div>")) {
                    pode = false;
                }
                if (pode == true) {
                    out.add(inputLine);
                }
            }
            // daqui pra baixo tem que ser separado depois para um metodo de limpeza da cifra.

            String conteudo = "";
            for(String s : out) {
                conteudo += "<br>"+s+"</br>";
            }

            if (conteudo!= "") {
                ExibeCifraPesquisaAct.cifraMusica = conteudo;
                try {
                    Thread.sleep(50);
                    for (int i=0; i<100; i++) {
                        publishProgress();
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                return null;
            }
            in.close();
        } catch (IOException e) {
            Log.e("TAG_ASYNC_TASK", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        total += PROGRESSO;
        progressBar.incrementProgressBy(PROGRESSO);
        texto.setText(total + "%");

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List result) {
        super.onPostExecute(result);
        if (ExibeCifraPesquisaAct.cifraMusica!= null){
            Intent i = new Intent();
            i.setClass(context, ExibeCifraPesquisaAct.class);
            context.startActivity(i);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            exibir.setClickable(true);
            texto.setText("Verifique o nome da Musica digitada e sua conexão com a Internet.");
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }
}