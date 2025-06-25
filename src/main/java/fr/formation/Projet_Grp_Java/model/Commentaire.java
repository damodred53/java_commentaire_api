package fr.formation.Projet_Grp_Java.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Commentaire {
    @Id
    @UuidGenerator
    private String id;

    private String texte;

    private int qualiteProduit;
    private int rapportQualitePrix;
    private int faciliteUtilisation;

    @Transient
    private double noteMoyenne;

    @ManyToOne
    private Produit produit;

    public double getNoteMoyenne() {
        return (qualiteProduit + rapportQualitePrix + faciliteUtilisation) / 3.0;
    }
}