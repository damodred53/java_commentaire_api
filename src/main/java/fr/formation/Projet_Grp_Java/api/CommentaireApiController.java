package fr.formation.Projet_Grp_Java.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fr.formation.Projet_Grp_Java.model.Commentaire;
import fr.formation.Projet_Grp_Java.model.Produit;
import fr.formation.Projet_Grp_Java.repo.CommentaireRepository;
import fr.formation.Projet_Grp_Java.repo.ProduitRepository;
import fr.formation.Projet_Grp_Java.request.CreateCommentaireRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commentaire")
@RequiredArgsConstructor
public class CommentaireApiController {

    private final CommentaireRepository commentaireRepository;
    private final ProduitRepository produitRepository;

    @GetMapping("/note/by-produit-id/{produitId}")
    public List<Commentaire> getCommentairesByProduit(@PathVariable String produitId) {
        UUID uuid = UUID.fromString(produitId);
        return commentaireRepository.findAllByProduitId(uuid);
    }

    // @GetMapping("/note/by-produit-id/{produitId}")
    // public int getNoteByProduitId(@PathVariable String produitId) {
    // UUID uuid = UUID.fromString(produitId);
    // List<Commentaire> commentaires =
    // commentaireRepository.findAllByProduitId(uuid);

    // return (int) commentaires.stream()
    // .mapToInt(Commentaire::getNote)
    // .average()
    // .orElse(-1);
    // }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createCommentaire(@RequestBody CreateCommentaireRequest request) {
        Produit produit = produitRepository.findById(UUID.fromString(request.getProduitId()))
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));

        Commentaire commentaire = new Commentaire();
        BeanUtils.copyProperties(request, commentaire);

        commentaire.setProduit(produit);

        commentaireRepository.save(commentaire);
        return commentaire.getId();
    }
}
