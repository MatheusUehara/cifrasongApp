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
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.negocio.UsuarioService;

public class EditarEmailAct extends Activity {
    final UsuarioService negocio = new UsuarioService(EditarEmailAct.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_editar_email);


        final EditText senha = (EditText) findViewById(R.id.senha);
        final EditText email = (EditText) findViewById(R.id.email);
        final Button salvar = (Button) findViewById(R.id.salvar);

        email.setText(Session.usuarioLogado.getEmail());

        salvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    if (negocio.editarEmail(senha.getText().toString(),email.getText().toString())) {
                        Toast.makeText(EditarEmailAct.this, "Email atualizado com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.setClass(EditarEmailAct.this, MenuActivity.class);
                        startActivity(i);
                    }
                }catch(Exception e){
                        Toast.makeText(EditarEmailAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}