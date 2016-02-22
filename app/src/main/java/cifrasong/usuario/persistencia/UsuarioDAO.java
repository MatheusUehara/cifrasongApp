package cifrasong.usuario.persistencia;

import android.content.ContentValues;
import android.database.Cursor;

import cifrasong.database.CifraSongSQLiteHelper;
import cifrasong.database.DAO;
import cifrasong.usuario.dominio.Session;
import cifrasong.usuario.dominio.Usuario;

public class UsuarioDAO extends DAO {

    private static final UsuarioDAO instance = new UsuarioDAO();

    private UsuarioDAO() {
        super();
    }

    public static UsuarioDAO getInstance() {
        return instance;
    }

    private static CifraSongSQLiteHelper database = getDataBaseHelper();

    /**
     * metodo que adiciona o usuario no banco de dados
     *
     * @param usuario
     */
    public void adicionarUsuario(Usuario usuario) {
        open();
        ContentValues initialValues = new ContentValues();
        initialValues.put(database.getLogin(), usuario.getLogin());
        initialValues.put(database.getSenha(), usuario.getSenha());
        initialValues.put(database.getEmail(), usuario.getEmail());
        getDatabase().insert(database.getTableNameUsuario(), null, initialValues);
        close();
    }

    /**
     * consulta no banco se os dados de login/senha ou email/senha conferem com os digitados
     *
     * @param senha
     * @param login
     * @return condition
     */
    public Usuario login(String login, String senha) {
        Usuario condition = null;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE login=? AND senha=?", new String[]{login, senha});
        Cursor mCursor1 = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE email=? AND senha=?", new String[]{login, senha});
        if (((mCursor != null) && (mCursor.getCount() > 0)) || ((mCursor1 != null) && (mCursor1.getCount() > 0))) {
            if (((mCursor != null) && (mCursor.getCount() > 0))) {
                mCursor.moveToFirst();
                Usuario novoUsuario = new Usuario();
                novoUsuario.setID(mCursor.getInt(mCursor.getColumnIndex(database.getIdUsuario())));
                novoUsuario.setLogin(mCursor.getString(mCursor.getColumnIndex(database.getLogin())));
                novoUsuario.setEmail(mCursor.getString(mCursor.getColumnIndex(database.getEmail())));
                novoUsuario.setSenha(mCursor.getString(mCursor.getColumnIndex(database.getSenha())));
                condition = novoUsuario;
                close();
            } else if (((mCursor1 != null) && (mCursor1.getCount() > 0))) {
                mCursor1.moveToFirst();
                Usuario novoUsuario = new Usuario();
                novoUsuario.setID(mCursor1.getInt(mCursor1.getColumnIndex(database.getIdUsuario())));
                novoUsuario.setLogin(mCursor1.getString(mCursor1.getColumnIndex(database.getLogin())));
                novoUsuario.setEmail(mCursor1.getString(mCursor1.getColumnIndex(database.getEmail())));
                novoUsuario.setSenha(mCursor1.getString(mCursor1.getColumnIndex(database.getSenha())));
                condition = novoUsuario;
                close();
            }
        }
        return condition;
    }

    /**
     * metodo que confere se ja existe o e-mail cadastrado
     *
     * @param usuario
     * @return condition
     */
    public boolean existeEmail(Usuario usuario) {
        boolean condition = false;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE email=?", new String[]{(usuario.getEmail())});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                condition = true;
            }
        }
        return condition;
    }

    /**
     * metodo que confere se exite o usuario cadastrado
     *
     * @param usuario
     * @return condition
     */
    public boolean existeUsuario(Usuario usuario) {
        boolean condition = false;
        open();
        Cursor mCursor = getDatabase().rawQuery("SELECT * FROM " + database.getTableNameUsuario() + " WHERE login=?", new String[]{(usuario.getLogin())});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                condition = true;
            }
        }
        close();
        return condition;
    }

    /**
     * metodo que exclui o usuario do banco de dados
     *
     * @param usuario
     * @return true
     */
    public boolean deleteUsuario(Usuario usuario) {
        open();
        getDatabase().execSQL("DELETE FROM " + database.getTableNameUsuario() + " WHERE login = ?", new String[]{usuario.getLogin()});
        close();
        return true;
    }

    /**
     * metodo que altera a senha
     *
     * @param senha
     * @return true
     */
    public boolean alterarSenha(String senha) {
        open();
        getDatabase().execSQL("UPDATE  " + database.getTableNameUsuario() + " SET " + database.getSenha() + " = ? WHERE login = ?", new String[]{senha, Session.usuarioLogado.getLogin()});
        close();
        return true;
    }

    /**
     * metodo que altera o e-mail ja existene pelo novo e atualiza
     *
     * @param email
     */
    public void alterarEmail(String email) {
        open();
        getDatabase().execSQL("UPDATE  " + database.getTableNameUsuario() + " SET " + database.getEmail() + " = ?  WHERE login = ?", new String[]{email, Session.usuarioLogado.getLogin()});
        close();
    }

}