package fr.formation.Projet_Grp_Java.api;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.Projet_Grp_Java.exception.ProduitNotFoundException;
import fr.formation.Projet_Grp_Java.model.Commentaire;
import fr.formation.Projet_Grp_Java.model.Produit;
import fr.formation.Projet_Grp_Java.repo.CommentaireRepository;
import fr.formation.Projet_Grp_Java.repo.ProduitRepository;
import fr.formation.Projet_Grp_Java.request.CreateOrUpdateProduitRequest;
import fr.formation.Projet_Grp_Java.response.CommentaireResponse;
import fr.formation.Projet_Grp_Java.response.ProduitByIdResponse;
import fr.formation.Projet_Grp_Java.response.ProduitResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produit")
@RequiredArgsConstructor
public class ProduitApiController {
    private final ProduitRepository repository;
    private final CommentaireRepository commentaireRepository;

    @GetMapping
    public List<ProduitResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(p -> {
                    int note = (int) this.commentaireRepository.findAllByProduitId(p.getId())
                            .stream()
                            .mapToInt(c -> (int) c.getNoteMoyenne())
                            .average()
                            .orElse(-1);

                    return ProduitResponse.builder()
                            .id(p.getId().toString())
                            .nom(p.getNom())
                            .prix(p.getPrix())
                            .note(note)
                            .build();
                })
                .toList();
    }

    @GetMapping("/{id}")
    public ProduitByIdResponse findById(@PathVariable String id) {
        java.util.UUID uuid = java.util.UUID.fromString(id);
        Produit produit = this.repository.findById(uuid).orElseThrow(ProduitNotFoundException::new);
        List<Commentaire> commentaires = this.commentaireRepository.findAllByProduitId(uuid);
        ProduitByIdResponse resp = new ProduitByIdResponse();

        int note = (int) commentaires
                .stream()
                .mapToInt(c -> (int) c.getNoteMoyenne())
                .average()
                .orElse(-1);

        BeanUtils.copyProperties(produit, resp);

        resp.setCommentaires(commentaires
                .stream()
                .map(c -> CommentaireResponse.builder()
                        .texte(c.getTexte())
                        .note((int) Math.round(c.getNoteMoyenne()))
                        .build())
                .toList());

        resp.setNote(note);

        return resp;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateOrUpdateProduitRequest request) {
        Produit produit = new Produit();

        BeanUtils.copyProperties(request, produit);

        this.repository.save(produit);

        return produit.getId().toString();
    }
}