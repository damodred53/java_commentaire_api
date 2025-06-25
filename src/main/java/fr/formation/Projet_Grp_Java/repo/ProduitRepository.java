package fr.formation.Projet_Grp_Java.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.Projet_Grp_Java.model.Produit;
import java.util.UUID;

public interface ProduitRepository extends JpaRepository<Produit, UUID> {

}
