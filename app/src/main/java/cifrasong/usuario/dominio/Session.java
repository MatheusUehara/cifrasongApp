package cifrasong.usuario.dominio;

import android.content.Context;

import cifrasong.cifra.dominio.Cifra;

/**
 * Created by Raissa on 17/11/2014.
 */

public class Session {

    public static Cifra cifraSelecionada;

    public static Usuario usuarioLogado;

    private static Context contexto = null;

    public static void setCifraSelecionada(Cifra cifra) {
        cifraSelecionada = cifra;
    }

    public static Cifra getCifraSelecionada() {
        return cifraSelecionada;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setContexto(Context context) {
        contexto = context;
    }

    public static Context getContexto() {
        return contexto;
    }
}
