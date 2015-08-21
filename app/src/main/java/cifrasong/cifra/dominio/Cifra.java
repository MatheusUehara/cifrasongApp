package cifrasong.cifra.dominio;

/**
 * Created by Igor on 24/11/2014.
 */
public class Cifra {

    private int id;
    private String nome;
    private String artista;
    private int favorito;
    private String conteudo;


    public Cifra(){}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }
    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getFavorito() {
        return favorito;
    }
    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public String getConteudo() {
        return conteudo;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }


}
