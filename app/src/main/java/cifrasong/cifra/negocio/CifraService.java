package cifrasong.cifra.negocio;

import android.content.Context;

import cifrasong.cifra.dominio.Cifra;
import cifrasong.cifra.persistencia.CifraDAO;

/**
 * Created by Igor on 24/11/2014.
 */

public class CifraService {

    private CifraDAO CifraDAO = null;

    public CifraService(Context context) {
        this.CifraDAO = CifraDAO.getInstance();
        this.CifraDAO.setContextUp(context);
    }

    /**
     * segue o padraoo do site cifraClub e monta o link para pesquisar a cifra troca os espaacos digitados por -
     *
     * @param sufixo
     * @return String retorno
     */
    public static String montaLink(String sufixo) {
        StringBuilder retorno = new StringBuilder("http://www.cifraclub.com.br/");

        char espaco[] = {' '};

        for (int i = 0; i < sufixo.length(); i++) {
            if (sufixo.charAt(i) == espaco[0]) {
                retorno.append("-");
            } else {
                retorno.append(sufixo.charAt(i));
            }
        }
        return retorno.toString();
    }

    /**
     * adiciona a cifra pesqisada no banco
     *
     * @param cifra
     */
    public void adicionarCifra(Cifra cifra) {
        if (!CifraDAO.existeCifra(cifra)) {
            CifraDAO.adicionarCifra(cifra);
        }
    }

    /**
     * exclui todas as cifras do usuario
     */
    public void deletarTodasCifras() {
        CifraDAO.deletarTodasCifras();
    }

    /**
     * exclui a cifra passada pelo usuario
     * por padrão a cifra a ser deletada é sempre a cifraSelecionada da Session
     */
    public void deletarCifra() {
        CifraDAO.deleteCifra();
    }

    /**
     * verifica as as exceções e coloca a cifra em meus favoritos
     *
     * @param cifra
     * @return boolean cifraFavoritada
     */
    public boolean favoritarCifra(Cifra cifra) throws Exception {
        StringBuilder message = new StringBuilder();
        boolean cifraFavoritada = false;
        if (CifraDAO.favoritarCifra(cifra)) {
            cifraFavoritada = true;
        } else {
            message.append("A cifra não pode ser favoritada por que ja é favorita.");
            throw new Exception(message.toString());
        }
        return cifraFavoritada;
    }

    /**
     * exclui a cifra de meus favoritos
     *
     * @param cifra
     * @return boolean cifraDesFavoritada
     */
    public boolean desfavoritarCifra(Cifra cifra) throws Exception {
        StringBuilder message = new StringBuilder();
        boolean cifraDesFavoritada = false;
        if (CifraDAO.desfavoritarCifra(cifra)) {
            cifraDesFavoritada = true;
        } else {
            message.append("Desculpe ocorreu um erro :/");
            throw new Exception(message.toString());
        }
        return cifraDesFavoritada;
    }
}
