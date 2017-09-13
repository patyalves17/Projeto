package com.projeto.patyalves.projeto.model;

/**
 * Created by logonrm on 25/07/2017.
 */

public class User {

    private int id;

    private String usuario;
    private String senha;
    private String tokenTwitter;
    private String secretTwitter;
    private String UserIdTwitter;
    private Long idpessoa;

    public User(String usuario, String senha) {
        this.setUsuario(usuario);
        this.setSenha(senha);
    }
    public User(int id, String usuario, String senha) {
        this.id=id;
        this.setUsuario(usuario);
        this.setSenha(senha);
    }

    public User(int id, String usuario, String senha, String tokenTwitter, String secretTwitter, String userIdTwitter, Long idpessoa) {
        this.id=id;
        this.setUsuario(usuario);
        this.setSenha(senha);
        this.setTokenTwitter(tokenTwitter);
        this.setSecretTwitter(secretTwitter);
        this.setUserIdTwitter(userIdTwitter);
        this.setIdpessoa(idpessoa);
    }

    public User() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTokenTwitter() {
        return tokenTwitter;
    }

    public void setTokenTwitter(String tokenTwitter) {
        this.tokenTwitter = tokenTwitter;
    }

    public String getSecretTwitter() {
        return secretTwitter;
    }

    public void setSecretTwitter(String secretTwitter) {
        this.secretTwitter = secretTwitter;
    }

    public String getUserIdTwitter() {
        return UserIdTwitter;
    }

    public void setUserIdTwitter(String userIdTwitter) {
        UserIdTwitter = userIdTwitter;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(Long idpessoa) {
        this.idpessoa = idpessoa;
    }
}
