
package fr.formation.Projet_Grp_Java.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentaireRequest {
    private String texte;
    private int qualiteProduit;
    private int rapportQualitePrix;
    private int faciliteUtilisation;
    private String produitId;
}
