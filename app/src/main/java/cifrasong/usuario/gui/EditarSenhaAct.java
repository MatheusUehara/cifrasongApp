package cifrasong.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cifrasong.R;
import cifrasong.usuario.negocio.UsuarioService;


public class EditarSenhaAct extends android.support.v7.app.AppCompatActivity {
    final UsuarioService negocio = new UsuarioService(EditarSenhaAct.this);
    Toolbar toolbar;

    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_senha);
        setTitle("Alterar Senha");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText senha = (EditText) findViewById(R.id.senhaAtual);
        final EditText novaSenha = (EditText) findViewById(R.id.senhaNova);
        final EditText confirmarSenhaNova = (EditText) findViewById(R.id.senhaNovaConfirmacao);

        final Button salvar = (Button) findViewById(R.id.salvar);


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (negocio.editarSenha(novaSenha.getText().toString(), confirmarSenhaNova.getText().toString(), senha.getText().toString())) {
                        Toast.makeText(EditarSenhaAct.this, "Senha alterada com sucesso.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(EditarSenhaAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

