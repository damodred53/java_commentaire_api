package fr.formation.Projet_Grp_Java.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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

    // @GetMapping("/note/by-produit-id/{produitId}")
    // public List<Commentaire> getCommentairesByProduit(@PathVariable String
    // produitId) {
    // UUID uuid = UUID.fromString(produitId);
    // return commentaireRepository.findAllByProduitId(uuid);
    // }

    @GetMapping("/produit/{produitId}")
    public List<Commentaire> getCommentairesByProduitId(@PathVariable String produitId) {
        UUID uuid = UUID.fromString(produitId);
        return commentaireRepository.findAllByProduitId(uuid);
    }

    @GetMapping("/produit/nom/{nom}")
    public List<Commentaire> getCommentairesByProduitNom(@PathVariable String nom) {
        return commentaireRepository.findAllByProduitNom(nom);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCommentaire(@RequestBody CreateCommentaireRequest request) {

        String produitNom = produitFeignClient.getNameById(request.getProduitId());

        Commentaire commentaire = new Commentaire();

        commentaire.setProduitNom(produitNom);
        commentaire.setTexte(request.getTexte());
        commentaire.setQualiteProduit(request.getQualiteProduit());
        commentaire.setRapportQualitePrix(request.getRapportQualitePrix());
        commentaire.setFaciliteUtilisation(request.getFaciliteUtilisation());

        commentaireRepository.save(commentaire);

        return commentaire.getId();
    }

}
