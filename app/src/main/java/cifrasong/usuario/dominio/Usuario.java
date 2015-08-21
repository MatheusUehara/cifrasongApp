package cifrasong.usuario.dominio;


import java.util.ArrayList;

import cifrasong.cifra.dominio.Cifra;

/**
 * Created by Uehara on 19/10/2014.
 */

public class Usuario {
    private int id;
    private String login;
    private String email;
    private String senha;
    private ArrayList<Cifra> listaCifras ;
    private ArrayList<Cifra> listaFavorita ;

    public Usuario(){}

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Cifra> getListaCifras(){ return this.listaCifras;}

    public void setListaCifra(ArrayList<Cifra> cifras){this.listaCifras = cifras;}

    public ArrayList<Cifra> getListaFavorita(){ return this.listaFavorita;}

    public void setListaFavorita(ArrayList<Cifra> cifras){this.listaFavorita = cifras;}

    public int getID(){
        return this.id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public String getSenha(){
        return this.senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin(){
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
}