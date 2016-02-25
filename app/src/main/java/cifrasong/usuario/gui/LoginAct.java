package cifrasong.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cifrasong.R;
import cifrasong.usuario.negocio.UsuarioService;


public class LoginAct extends Activity {

    final UsuarioService negocio = new UsuarioService(LoginAct.this);

    public void limpaDados(EditText login, EditText senha) {
        login.setText("");
        senha.setText("");
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        final EditText login = (EditText) findViewById(R.id.login);
        final EditText senha = (EditText) findViewById(R.id.senha);
        final Button entrar = (Button) findViewById(R.id.entrar);
        final Button registrar = (Button) findViewById(R.id.registrar);
        final CheckBox checkBox =  (CheckBox) findViewById(R.id.checkBox);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (negocio.login(login.getText().toString(), senha.getText().toString()) == true) {
                        if (checkBox.isChecked()) {
                            SharedPreferences prefs = getSharedPreferences("LoginPrefs", 0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("estaLogado", true);
                            editor.putString("login", login.getText().toString());
                            editor.putString("senha", senha.getText().toString());
                            editor.commit();
                        }
                        Toast.makeText(LoginAct.this, "Logado com sucesso.", Toast.LENGTH_SHORT).show();
                        limpaDados(login, senha);
                        Intent i = new Intent(LoginAct.this, MenuActivity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                limpaDados(login, senha);
                Intent j = new Intent();
                j.setClass(LoginAct.this, CadastroAct.class);
                startActivity(j);
            }
        });
    }

}