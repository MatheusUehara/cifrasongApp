package cifrasong.usuario.gui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cifrasong.R;
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.negocio.UsuarioService;

public class EditarEmailAct extends android.support.v7.app.AppCompatActivity {
    final UsuarioService negocio = new UsuarioService(EditarEmailAct.this);
    Toolbar toolbar;

    public void onBackPressed() {
        Intent intent = new Intent(EditarEmailAct.this, ConfiguracaoAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_email);
        setTitle("Alterar E-mail");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditarEmailAct.this, ConfiguracaoAct.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        final EditText senha = (EditText) findViewById(R.id.senha);
        final EditText email = (EditText) findViewById(R.id.email);
        final Button salvar = (Button) findViewById(R.id.salvar);

        email.setText(Session.usuarioLogado.getEmail());

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (negocio.editarEmail(senha.getText().toString(), email.getText().toString())) {
                        Toast.makeText(EditarEmailAct.this, "Email atualizado com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.setClass(EditarEmailAct.this, MenuActivity.class);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    Toast.makeText(EditarEmailAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}