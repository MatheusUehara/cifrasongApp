package cifrasong.usuario.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cifrasong.R;
import cifrasong.usuario.dominio.Usuario;
import cifrasong.usuario.negocio.UsuarioService;

public class CadastroAct extends Activity {
    final UsuarioService negocio = new UsuarioService(CadastroAct.this);

    public void limpaDados(EditText login,EditText email,EditText senha,EditText confirmarSenha){
        login.setText("");
        email.setText("");
        senha.setText("");
        confirmarSenha.setText("");
    };

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.setClass(CadastroAct.this, LoginAct.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cadastro);

        final EditText login = (EditText) findViewById(R.id.login);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText senha = (EditText) findViewById(R.id.senha);
        final EditText confirmarSenha = (EditText) findViewById(R.id.confirmarSenha);

        final Button registrar = (Button) findViewById(R.id.registrar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {				
				Usuario usuario = new Usuario();
                usuario.setLogin(login.getText().toString());
                usuario.setSenha(senha.getText().toString());
                usuario.setEmail(email.getText().toString());
                String formConfirmarSenha = confirmarSenha.getText().toString();

				try {					
					negocio.adicionar(usuario,formConfirmarSenha);
					Toast.makeText(CadastroAct.this,"Adicionado com sucesso.",Toast.LENGTH_SHORT).show();
                    limpaDados(login,email,senha,confirmarSenha);
					Intent i = new Intent();
					i.setClass(CadastroAct.this,LoginAct.class);
                    startActivity(i);

                }catch (Exception e) {
                    Toast.makeText(CadastroAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
            }
        });
    }
}
