package cifrasong.usuario.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cifrasong.R;
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.negocio.UsuarioService;

public class ConfiguracaoAct extends android.support.v7.app.AppCompatActivity {
    Toolbar toolbar;
    final UsuarioService negocio = new UsuarioService(ConfiguracaoAct.this);
    final CifraService cifraNegocio = new CifraService(ConfiguracaoAct.this);

    public void onBackPressed() {
        Intent intent = new Intent(ConfiguracaoAct.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        setTitle("Configurações da conta");
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracaoAct.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Alterar E-mail");
        itens.add("Alterar Senha");
        itens.add("Excluir Conta");

        ArrayAdapter<String> item_adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_dropdown_item_1line, itens);
        ListView lista = (ListView) findViewById(R.id.opcoes);
        lista.setAdapter(item_adapter);
        lista.setClickable(true);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent j = new Intent();
                    j.setClass(ConfiguracaoAct.this, EditarEmailAct.class);
                    startActivity(j);
                } else if (i == 1) {
                    Intent j = new Intent();
                    j.setClass(ConfiguracaoAct.this, EditarSenhaAct.class);
                    startActivity(j);
                } else if (i == 2) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConfiguracaoAct.this);
                    alertDialogBuilder.setTitle("Excluir usuário");
                    alertDialogBuilder.setMessage("Deseja excluir sua conta?");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                cifraNegocio.deletarTodasCifras();
                                negocio.deleteUsuario(Session.usuarioLogado);
                                Toast.makeText(ConfiguracaoAct.this, "Usuario excluido com sucesso.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent();
                                i.setClass(ConfiguracaoAct.this, LoginAct.class);
                                startActivity(i);
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(ConfiguracaoAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.show();
                }

            }
        });
    }
}



