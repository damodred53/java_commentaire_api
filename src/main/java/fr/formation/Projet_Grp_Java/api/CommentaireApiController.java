package fr.formation.Projet_Grp_Java.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fr.formation.Projet_Grp_Java.feignclient.ProduitFeignClient;
import fr.formation.Projet_Grp_Java.model.Commentaire;
import fr.formation.Projet_Grp_Java.repo.CommentaireRepository;
import fr.formation.Projet_Grp_Java.request.CreateCommentaireRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commentaire")
@RequiredArgsConstructor
public class CommentaireApiController {

    private final CommentaireRepository commentaireRepository;
    private final ProduitFeignClient produitFeignClient;

    // Méthode pour récupérer des commentaires par produit ID
    @GetMapping("/produit/{produitId}")
    public List<Commentaire> getCommentairesByProduitId(@PathVariable String produitId) {
        UUID uuid = UUID.fromString(produitId);
        return commentaireRepository.findAllByProduitId(uuid);
    }

    // Méthode pour récupérer des commentaires par nom de produit
    @GetMapping("/produit/nom/{nom}")
    public List<Commentaire> getCommentairesByProduitNom(@PathVariable String nom) {
        return commentaireRepository.findAllByProduitNom(nom);
    }

    // Méthode pour créer un commentaire
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCommentaire(@RequestBody CreateCommentaireRequest request) {

        // Appel à l'API produit pour récupérer le nom (fallback géré ici)
        String produitNom = produitFeignClient.getNameById(request.getProduitId());

        // Création du commentaire
        Commentaire commentaire = new Commentaire();
        commentaire.setProduitNom(produitNom); // Utilise le produitNom récupéré
        commentaire.setTexte(request.getTexte());
        commentaire.setQualiteProduit(request.getQualiteProduit());
        commentaire.setRapportQualitePrix(request.getRapportQualitePrix());
        commentaire.setFaciliteUtilisation(request.getFaciliteUtilisation());

        // Sauvegarde du commentaire dans le repository
        commentaireRepository.save(commentaire);

        // Retourne l'ID du commentaire créé
        return commentaire.getId();
    }
}
