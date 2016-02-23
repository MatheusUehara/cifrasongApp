package cifrasong.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import cifrasong.R;
import cifrasong.usuario.negocio.UsuarioService;

/**
 * Created by Uehara on 16/07/2015.
 */
public class SplashAct extends Activity implements Runnable {

    final UsuarioService negocio = new UsuarioService(SplashAct.this);

    private Handler handler;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(this, 2000);
        final ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.ic_splash);


    }


    @Override

    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }


    @Override

    public void run() {
        // Faça o carregamento necessário aqui...
        // Depois abre a atividade principal e fecha esta

        SharedPreferences prefs = getSharedPreferences("LoginPrefs", 0);
        boolean jaLogou = prefs.getBoolean("estaLogado", false);

        if(jaLogou) {
            try {
                if (negocio.login(prefs.getString("login",""),prefs.getString("senha","")) == true) {
                    Intent i = new Intent(SplashAct.this, MenuActivity.class);
                    startActivity(i);
                }else{
                Intent it = new Intent(SplashAct.this, LoginAct.class);
                startActivity(it);
                }
            } catch (Exception e) {
                Toast.makeText(SplashAct.this, "Sessao expirada!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(SplashAct.this, LoginAct.class);
                startActivity(it);
            }
        }else {
            Intent it = new Intent(SplashAct.this, LoginAct.class);
            startActivity(it);
        }
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
