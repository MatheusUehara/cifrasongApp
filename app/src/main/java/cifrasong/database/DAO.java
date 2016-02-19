package cifrasong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Uehara on 08/11/2014.
 */
public abstract class DAO {

    private static CifraSongSQLiteHelper dataBaseHelper;
    private static SQLiteDatabase database;
    private Context context = null;

    public void setContextUp(Context context) {
        setAtributes(context);
    }

    protected void setAtributes(Context ctx) {
        setContext(ctx);
        setBDHelper(new CifraSongSQLiteHelper(ctx));

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static CifraSongSQLiteHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public void setBDHelper(CifraSongSQLiteHelper CifraSongSQLiteHelper) {
        this.dataBaseHelper = CifraSongSQLiteHelper;
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public void open() {
        setDatabase(getDataBaseHelper().getWritableDatabase());
    }

    public void close() {
        getDataBaseHelper().close();
    }


}