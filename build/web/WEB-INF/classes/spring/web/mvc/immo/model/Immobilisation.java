package spring.web.mvc.immo.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author UCER
 */
@Entity
@Table(name = "immobilisation")
public class Immobilisation extends BaseModel {

    @Column(name = "article")
    private String article;

    @Column(name = "prixachat")
    private float prixAchat;

    @Column(name = "dateachat")
    private String dateAchat;

    @Column(name = "typeammortissement")
    private String typeAmmortissement;

    @Column(name = "duree")
    private int duree;

    @Column(name = "detenteur")
    private String detenteur;

    @Column(name = "datedemiseenservice")
    private String dateDeMiseEnService;

    public Immobilisation() {

    }

    public Immobilisation(String article, float prixAchat, String dateAchat, String typeAmmortissement, int duree, String detenteur, String dateDeMiseEnService) {
        this.article = article;
        this.prixAchat = prixAchat;
        this.dateAchat = dateAchat;
        this.typeAmmortissement = typeAmmortissement;
        this.duree = duree;
        this.detenteur = detenteur;
        this.dateDeMiseEnService = dateDeMiseEnService;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public float getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(float prixAchat) {
        this.prixAchat = prixAchat;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getTypeAmmortissement() {
        return typeAmmortissement;
    }

    public void setTypeAmmortissement(String typeAmmortissement) {
        this.typeAmmortissement = typeAmmortissement;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDetenteur() {
        return detenteur;
    }

    public void setDetenteur(String detenteur) {
        this.detenteur = detenteur;
    }

    public String getDateDeMiseEnService() {
        return dateDeMiseEnService;
    }

    public void setDateDeMiseEnService(String dateDeMiseEnService) {
        this.dateDeMiseEnService = dateDeMiseEnService;
    }
}