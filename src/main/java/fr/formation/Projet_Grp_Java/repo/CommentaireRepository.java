package fr.formation.Projet_Grp_Java.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.Projet_Grp_Java.model.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, String> {
    public List<Commentaire> findAllByProduitNom(String produitNom);

    List<Commentaire> findAllByProduitId(UUID produitId);
}