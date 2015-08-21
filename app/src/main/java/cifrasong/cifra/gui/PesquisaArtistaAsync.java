package cifrasong.cifra.gui;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cifrasong.cifra.dominio.Cifra;


/**
 * Created by Uehara on 30/11/2014.
 */

public class PesquisaArtistaAsync extends AsyncTask <Void, Object, Cifra> {

    private String endereco = "";
    private ProgressBar progressBar;
    private TextView texto;
    private int total = 0;
    private static int PROGRESSO = 25;
    private Button exibir;
    String artista = "";

    public PesquisaArtistaAsync(Context context, ProgressBar progressBar, TextView texto ,String endereco , Button exibir,String artista) {
        this.progressBar = progressBar;
        this.texto = texto;
        this.endereco = endereco;
        this.exibir = exibir;
        this.artista = artista;
    }

    @Override
    protected void onPreExecute() {
        texto.setText("0%");
        super.onPreExecute();
    }

    @Override
    protected Cifra doInBackground(Void... params) {
        ArrayList <Cifra> content = new ArrayList<Cifra>();
        try {
            URL url = new URL(this.endereco);
            URLConnection c = url.openConnection();
            c.setConnectTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String inputLine;
            boolean pode = false;

            String conteudo = "";

            while ((inputLine = in.readLine()) != null) {


                if (inputLine.contains("<ul class=\"art_musics js-art_songs")) {
                    pode = true;
                } else if (inputLine.contains("</ul>")) {
                    pode = false;
                }
                if (pode == true) {
                    inputLine = inputLine.replaceAll("ver mais cifras", " ");
                    conteudo += inputLine;
                }
            }


            String[] NomeCifras =  conteudo.split("<a href=\"/"+artista.replaceAll(" ","-")+"/");

            for (String s: NomeCifras){
                Pattern regexp_conteudo = Pattern.compile("([^<]+)/");
                Matcher match = regexp_conteudo.matcher(s);
                if(match.find()){
                    Cifra cifra = new Cifra();
                    cifra.setNome(match.group(1).replaceAll("-", " "));
                    cifra.setArtista(artista);
                    if ((!cifra.getNome().contains("a href")) && (!cifra.getNome().contains("class"))) {
                        content.add(cifra);
                    }
                }
            }

            if (content.size() > 0){
                PesquisaArtistaAct.cifras = content;
                try {
                    Thread.sleep(1000);
                    for (int i=0; i<4; i++) {
                        publishProgress();
                        Thread.sleep(1000);
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
    protected void onProgressUpdate (Object...values){
        total += PROGRESSO;
        progressBar.incrementProgressBy(PROGRESSO);
        texto.setText(total + "%");

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute (Cifra result){
        if (PesquisaArtistaAct.cifras != null ) {
            exibir.setClickable(true);
            texto.setText("Tarefa concluída toque novamente em Pesquisar para visualizar as Cifras.");
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            super.onPostExecute(result);

        }else{
            exibir.setClickable(true);
            texto.setText("Verifique o nome do Artista digitado e sua conexão com a Internet.");
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }
}