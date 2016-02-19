package cifrasong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Uehara on 08/11/2014.
 */
public class CifraSongSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cifrasongbd";

    private static int DATABASE_VERSION = 1;

    private static final String TAG = "DBAdapter";

    public CifraSongSQLiteHelper(Context context) {
        super(context, getDataBaseName(), null, getDATABASE_VERSION());
    }

    public static String getDataBaseName() {
        return DATABASE_NAME;
    }

    public static int getDATABASE_VERSION() {
        return DATABASE_VERSION;
    }

    public static void setDATABASE_VERSION(int DATABASE_VERSION) {
        CifraSongSQLiteHelper.DATABASE_VERSION = DATABASE_VERSION;
    }

    public static String getTag() {
        return TAG;
    }

    /*
    criacao da tabela usuario e suas colunas
    */

    private static final String LOGIN_USUARIO = "login";
    private static final String SENHA_USUARIO = "senha";
    private static final String EMAIL_USUARIO = "email";
    private static final String ID_USUARIO = "_id";

    private static final String TABLE_NAME_USUARIO = "usuario";

    private static final String TABLE_DATABASE_USUARIO_CREATE =
            "create table usuario (_id integer primary key autoincrement, " +
                    "login text not null, " +
                    "senha text not null, " +
                    "email text not null);";

    public static String getTableDatabaseUsuarioCreate() {
        return TABLE_DATABASE_USUARIO_CREATE;
    }

    public static String getEmail() {
        return EMAIL_USUARIO;
    }

    public static String getLogin() {
        return LOGIN_USUARIO;
    }

    public static String getSenha() {
        return SENHA_USUARIO;
    }

    public static String getIdUsuario() {
        return ID_USUARIO;
    }

    public static String getTableNameUsuario() {
        return TABLE_NAME_USUARIO;
    }


    /*
    Criação da tabela de cifra e suas colunas
    */

    private static final String ID_CIFRA = "_id";
    private static final String CIFRA = "cifra";
    private static final String NOME_CIFRA = "nome";
    private static final String ARTISTA_CIFRA = "artista";
    private static final String TABLE_NAME_CIFRA = "cifra";
    private static final String TABLE_DATABASE_CIFRA_CREATE =
            "create table cifra (_id integer primary key autoincrement, " +
                    "cifra text not null, " +
                    "nome text not null, " +
                    "artista text not null);";

    public static String getTableDatabaseCifraCreate() {
        return TABLE_DATABASE_CIFRA_CREATE;
    }

    public static String getCifra() {
        return CIFRA;
    }

    public static String getCifraIdCifra() {
        return ID_CIFRA;
    }

    public static String getCifraNome() {
        return NOME_CIFRA;
    }

    public static String getCifraArtista() {
        return ARTISTA_CIFRA;
    }

    public static String getTableNameCifra() {
        return TABLE_NAME_CIFRA;
    }


    /*
    criação da tabela de ralacionamento.
    */

    private static final String RELACIONAMENTO_ID = "_id";
    private static final String USUARIO_CIFRA_USUARIO_ID = "usuario_id";
    private static final String USUARIO_CIFRA_CIFRA_ID = "cifra_id";
    private static final String USUARIO_CIFRA_FAVORITO = "cifra_favorita";
    private static final String TABLE_NAME_USUARIO_CIFRA = "cifraUsuario";

    private static final String CREATE_TABLE_NAME_USUARIO_CIFRA =
            "create table cifraUsuario (_id integer primary key autoincrement, "
                    + "usuario_id integer, "
                    + "cifra_id integer,"
                    + "cifra_favorita integer,"
                    + "foreign key(usuario_id) references usuario(_id), "
                    + "foreign key(cifra_id) references cifra(_id));";

    public static String getTableDatabaseUsuarioCifraCreate() {
        return CREATE_TABLE_NAME_USUARIO_CIFRA;
    }

    public static String getTableNameUsuarioCifra() {
        return TABLE_NAME_USUARIO_CIFRA;
    }

    public static String getCifraFavorito() {
        return USUARIO_CIFRA_FAVORITO;
    }

    public static String getRelacionamentoId() {
        return RELACIONAMENTO_ID;
    }

    public static String getUsuarioCifraUsuarioId() {
        return USUARIO_CIFRA_USUARIO_ID;
    }

    public static String getUsuarioCifraCifraId() {
        return USUARIO_CIFRA_CIFRA_ID;
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL(getTableDatabaseUsuarioCreate());
        dataBase.execSQL(getTableDatabaseCifraCreate());
        dataBase.execSQL(getTableDatabaseUsuarioCifraCreate());
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int newVersion, int oldVersion) {
        Log.w(getTag(), "Atualizando o banco de dados da versao " + oldVersion
                + " para "
                + newVersion + ", que destruira todos os dados antigos");
        dataBase.execSQL("DROP TABLE IF EXISTS login");
        onCreate(dataBase);/// query para recriar o banco
        setDATABASE_VERSION(newVersion);
    }


}
