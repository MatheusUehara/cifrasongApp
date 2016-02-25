package cifrasong.cifra.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cifrasong.R;
import cifrasong.cifra.dominio.Cifra;
import cifrasong.cifra.negocio.CifraService;
import cifrasong.usuario.dominio.Session;

import android.widget.AbsListView.MultiChoiceModeListener;

/**
 * Created by Uehara on 01/12/2014.
 */
public class MinhaCifraAct extends Fragment {

    List<Cifra> cifras;
    ArrayAdapter cifrasAdapter;
    ListView lista;
    final CifraService negocio = new CifraService(this.getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // AQUI É A VIEW DO FRAGMENT ALGO MUITO IMPORTANTE QUE NÃO POSSO ME ESQUECER....
        View v = inflater.inflate(R.layout.activity_lista_cifras, container, false);

        cifras = Session.getUsuarioLogado().getListaCifras();

        lista = (ListView) v.findViewById(R.id.minhasCifras);
        // Pass results to ListViewAdapter Class

        final CifrasAdapter listviewadapter = new CifrasAdapter(this.getActivity(), R.layout.activity_lista_cifras,cifras);

        // Binds the Adapter to the ListView

        lista.setAdapter(listviewadapter);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        // Capture ListView item click
        lista.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = lista.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                listviewadapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = listviewadapter.getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Cifra selecteditem = listviewadapter.getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                listviewadapter.remove(selecteditem);
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                listviewadapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        final TextView info = (TextView) v.findViewById(R.id.info);

        if (Session.getUsuarioLogado().getListaCifras().size()<=0){
            info.setVisibility(TextView.VISIBLE);
        }

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cifra cifraSelecionada = (Cifra) lista.getItemAtPosition(i);
                Session.setCifraSelecionada(cifraSelecionada);

                Intent intent = new Intent(getActivity(), ExibeCifraAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);}

        });
        return v;
    }

        private class CifrasAdapter extends ArrayAdapter<Cifra> {
        private Context ctx;
        private SparseBooleanArray mSelectedItemsIds;
        List<Cifra> cifralist;
        LayoutInflater inflater;

        public CifrasAdapter(Context ctx, int resourceId,List<Cifra> cifraList) {
            super(ctx, resourceId, cifras);
            mSelectedItemsIds = new SparseBooleanArray();
            this.ctx = ctx;
            this.cifralist = cifraList;
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if (view == null) {
                view = getActivity().getLayoutInflater().from(this.ctx).inflate(R.layout.itens_list_view, null);
            }

            Cifra cifra = cifras.get(position);

            TextView nomeCifra = (TextView) view.findViewById(R.id.musicaCifra);
            TextView artistaCifra = (TextView) view.findViewById(R.id.artistaCifra);
            nomeCifra.setText(cifra.getNome());
            artistaCifra.setText(cifra.getArtista());

            return view;
        }

        public void remove(Cifra object) {

            cifralist.remove(object);
            notifyDataSetChanged();
            Session.getUsuarioLogado().getListaCifras().remove(object);
        }

        public List<Cifra> getCifra() {
            return cifralist;
        }

        public void toggleSelection(int position) {
            selectView(position, !mSelectedItemsIds.get(position));
        }

        public void removeSelection() {
            mSelectedItemsIds = new SparseBooleanArray();
            notifyDataSetChanged();
        }

        public void selectView(int position, boolean value) {
            if (value)
                mSelectedItemsIds.put(position, value);
            else
                mSelectedItemsIds.delete(position);
            notifyDataSetChanged();
        }

        public int getSelectedCount() {
            return mSelectedItemsIds.size();
        }

        public SparseBooleanArray getSelectedIds() {
            return mSelectedItemsIds;
        }
    }

}