/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.web.mvc.immo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author UCER
 */
@Entity
@Table(name = "ammortissement")
public class Ammortissement extends BaseModel {

    @Column(name = "annee")
    private float annee;
    
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "anterieur")
    private float anterieur;
    
    @Column(name = "exercice")
    private float exercice;
    
    @Column(name = "cumul")
    private float cumul;
    
    @Column(name = "vnc")
    private float vnc;

    public Ammortissement(float annee, String libelle, float anterieur, float exercice, float cumul, float vnc) {
        this.annee = annee;
        this.libelle = libelle;
        this.anterieur = anterieur;
        this.exercice = exercice;
        this.cumul = cumul;
        this.vnc = vnc;
    }

    public Ammortissement() {
    }

    public float getAnnee() {
        return annee;
    }

    public void setAnnee(float annee) {
        this.annee = annee;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getAnterieur() {
        return anterieur;
    }

    public void setAnterieur(float anterieur) {
        this.anterieur = anterieur;
    }

    public float getExercice() {
        return exercice;
    }

    public void setExercice(float exercice) {
        this.exercice = exercice;
    }

    public float getCumul() {
        return cumul;
    }

    public void setCumul(float cumul) {
        this.cumul = cumul;
    }

    public float getVnc() {
        return vnc;
    }

    public void setVnc(float vnc) {
        this.vnc = vnc;
    }
}
