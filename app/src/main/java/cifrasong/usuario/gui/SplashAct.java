package cifrasong.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import cifrasong.R;

/**
 * Created by Uehara on 16/07/2015.
 */
public class SplashAct extends Activity implements Runnable {


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
        Intent it = new Intent(SplashAct.this, LoginAct.class);
        startActivity(it);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
