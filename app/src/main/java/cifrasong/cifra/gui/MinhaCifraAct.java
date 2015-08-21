package cifrasong.cifra.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import java.util.List;

import cifrasong.R;
import cifrasong.cifra.dominio.Cifra;
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.gui.MenuActivity;

/**
 * Created by Uehara on 01/12/2014.
 */
public class MinhaCifraAct extends Fragment {

    List<Cifra> cifras;
    ArrayAdapter cifrasAdapter;
    ListView lista;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // AQUI É A VIEW DO FRAGMENT ALGO MUITO IMPORTANTE QUE NÃO POSSO ME ESQUECER....
        View v =inflater.inflate(R.layout.activity_lista_cifras,container,false);

        cifras = Session.getUsuarioLogado().getListaCifras();

        lista = (ListView) v.findViewById(R.id.minhasCifras);
        lista.setAdapter(new CifrasAdapter(getActivity()));

        final TextView info = (TextView)v.findViewById(R.id.info);
        if (Session.getUsuarioLogado().getListaCifras().size()<= 0){
            info.setVisibility(TextView.VISIBLE);
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cifra cifraSelecionada = (Cifra) lista.getItemAtPosition(i);
                Session.setCifraSelecionada(cifraSelecionada);

                Intent intent = new Intent(getActivity(), ExibeCifraAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return v;
    }


    private class CifrasAdapter extends ArrayAdapter<Cifra>{
        private Context ctx;

        public CifrasAdapter(Context ctx){
            super(ctx, R.layout.activity_lista_favoritos, cifras);
            this.ctx = ctx;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if (view == null){
                view = getActivity().getLayoutInflater().from(this.ctx).inflate(R.layout.itens_list_view, null);
            }

            Cifra cifra = cifras.get(position);

            TextView nomeCifra = (TextView) view.findViewById(R.id.musicaCifra);
            TextView artistaCifra = (TextView) view.findViewById(R.id.artistaCifra);
            nomeCifra.setText(cifra.getNome());
            artistaCifra.setText(cifra.getArtista());

            return view;
        }

    }
}