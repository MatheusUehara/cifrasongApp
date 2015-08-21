package cifrasong.cifra.persistencia;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import cifrasong.cifra.dominio.Cifra;
import cifrasong.database.CifraSongSQLiteHelper;
import cifrasong.database.DAO;
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.dominio.Usuario;

/**
 * Created by Igor on 24/11/2014.
 */

public class CifraDAO extends DAO{

    private static final CifraDAO instance = new CifraDAO();

    private CifraDAO() {
        super();
    }



    public static CifraDAO getInstance(){
        return instance;
    }

    private static CifraSongSQLiteHelper database = getDataBaseHelper();

    /**metodo que adiciona cifra
     * @param cifra
     */
    public void adicionarCifra(Cifra cifra) {
        open();
        ContentValues initialValues = new ContentValues();
        initialValues.put(database.getCifra(), cifra.getConteudo());
        initialValues.put(database.getCifraNome(), cifra.getNome());
        initialValues.put(database.getCifraArtista(),cifra.getArtista());
        getDatabase().insert(database.getTableNameCifra(), null, initialValues);
        close();
        adicionarCifraUsuario(cifra,1);
    }
    /**metodo para adiciona a cifra selecionada na lista do usuario logado
     * @param cifra
     * @param modo
     * modo 1 = cifra ainda não existia no app acabou de ser adicionada
     * modo 2 = cifra ja pertence a outro usuario vai ser adicionada a tabela de relacionamento
     */
    public void adicionarCifraUsuario(Cifra cifra, int modo){
        open();
        if (modo == 1) {
            Cursor mCursor = getDatabase().rawQuery("SELECT MAX(_id) FROM " + database.getTableNameCifra(), null);
            mCursor.moveToFirst();
            String idSQL = Integer.toString(mCursor.getInt(0));
            ContentValues cv = new ContentValues();
            cv.put(database.getUsuarioCifraUsuarioId(), Session.getUsuarioLogado().getID());
            cv.put(database.getUsuarioCifraCifraId(), idSQL);
            cv.put(database.getCifraFavorito(), "0");
            getDatabase().insert(database.getTableNameUsuarioCifra(), null, cv);
        }else if (modo == 2){
            Cursor mCursor1 = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuarioCifra() +" WHERE usuario_id = ? AND cifra_id = ?", new String[]{Integer.toString(Session.getUsuarioLogado().getID()),Integer.toString(cifra.getId())});
            if (mCursor1 != null) {
                if (mCursor1.getCount() > 0) {
                    return;
                }else{
                    ContentValues cv = new ContentValues();
                    cv.put(database.getUsuarioCifraUsuarioId(), Session.getUsuarioLogado().getID());
                    cv.put(database.getUsuarioCifraCifraId(), cifra.getId());
                    cv.put(database.getCifraFavorito(), "0");
                    getDatabase().insert(database.getTableNameUsuarioCifra(), null, cv);
                }
            }
        }
        close();
        retrieveCifras(Session.getUsuarioLogado());
    }
    /**metodo que da um select no banco e pega a cifra pelo id dela
     * @param i
     * @return cifra
     */
    public Cifra getCifra(int i){
        String idSQL = Integer.toString(i);
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM "+database.getTableNameCifra()+" WHERE _id = ?", new String[]{idSQL});
        mCursor.moveToFirst();
        Cifra cifra = new Cifra();
        cifra.setConteudo(mCursor.getString(1));
        cifra.setNome(mCursor.getString(2));
        cifra.setArtista(mCursor.getString(3));
        cifra.setId(i);
        close();
        return cifra;
    }
    /**metodo que é usado para carregar e para atualiza as listas de cifras do usuario
     *@param user
     */
	public void retrieveCifras(Usuario user){
        ArrayList<Integer> cifraIds = new ArrayList<Integer>();
        ArrayList<Integer> cifraIdsFav = new ArrayList<Integer>();
        ArrayList<Cifra> cifras = new ArrayList<Cifra>();
        ArrayList<Cifra> cifrasFavoritas = new ArrayList<Cifra>();
        String idSQL = Integer.toString(user.getID());
        open();
        Cursor cursorCifras = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuarioCifra() + " WHERE usuario_id = ?", new String[]{idSQL});
        if (cursorCifras != null){
            cursorCifras.moveToFirst();
            for (int x = 0 ; x<cursorCifras.getCount(); x++){
                cifraIds.add(cursorCifras.getInt(2));
                if (cursorCifras.getInt(3) == 1){
                    cifraIdsFav.add(cursorCifras.getInt(2));
                }
                cursorCifras.moveToNext();
            }
        }
        close();

        if (!cifraIds.isEmpty()){
            for (int i : cifraIds){
                cifras.add(this.getCifra(i));
            }
        }
        Session.getUsuarioLogado().setListaCifra(cifras);

        if (!cifraIdsFav.isEmpty()){
            for (int i : cifraIdsFav){
                Cifra fav = this.getCifra(i);
                fav.setFavorito(1);
                cifrasFavoritas.add(fav);
            }
        }
        Session.getUsuarioLogado().setListaFavorita(cifrasFavoritas);
    }

    /**verifica se ja¡ existe a cifra no banco
     *@param cifra
     * @return condition
     */
    public boolean existeCifra(Cifra cifra){
        boolean condition = false;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameCifra() + " WHERE nome=? AND artista = ?", new String[]{cifra.getNome(),cifra.getArtista()});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                condition = true;                
                mCursor.moveToFirst();
                Cifra cifra1 = new Cifra();
                cifra1.setConteudo(mCursor.getString(1));
                cifra1.setNome(mCursor.getString(2));
                cifra1.setArtista(mCursor.getString(3));
                cifra1.setId(mCursor.getInt(0));
                adicionarCifraUsuario(cifra1,2);
            }
        }
        close();
        return condition;
    }

    /**metodo que atualiza a tabela de relacionamento da cifraSelecionada de não favorita para favorita.
     * @param cifra
     * @return true
     */
    public boolean favoritarCifra(Cifra cifra){
        String idSQL = Integer.toString(cifra.getId());
        open();
        getDatabase().execSQL("UPDATE  " + database.getTableNameUsuarioCifra() + " SET cifra_favorita = 1 WHERE cifra_favorita = 0 AND  cifra_id = ?", new String[]{idSQL});
        close();
        retrieveCifras(Session.getUsuarioLogado());
        return true;
    }

    /**metodo para deletar todas as cifras do usuario logado
     * Usado quando o usuario deseja apagar a conta
     * */
    public void deletarTodasCifras(){
        String id = Integer.toString(Session.getUsuarioLogado().getID());
        open();
        getDatabase().execSQL("DELETE FROM " + database.getTableNameUsuarioCifra() + " WHERE usuario_id = ?", new String[]{id});
        close();
    }

    /**metodo para desfavoritar a cifraSelecionada.
     * @param cifra
     * @return true
     */
    public boolean desfavoritarCifra(Cifra cifra){
        String idSQL = Integer.toString(cifra.getId());
        open();
        getDatabase().execSQL("UPDATE  " + database.getTableNameUsuarioCifra() + " SET cifra_favorita = 0 WHERE cifra_favorita = 1 AND  cifra_id = ?", new String[]{idSQL});
        close();
        retrieveCifras(Session.getUsuarioLogado());
        return true;
    }

    /**deleta a cifra do banco*/
    public void deleteCifra() {
        String id = Integer.toString(Session.getCifraSelecionada().getId());
        open();
        getDatabase().execSQL("DELETE FROM " + database.getTableNameUsuarioCifra() + " WHERE usuario_id = ? AND cifra_id = ? ", new String[]{String.valueOf(Session.getUsuarioLogado().getID()), id});
        close();
        retrieveCifras(Session.getUsuarioLogado());
    }
}