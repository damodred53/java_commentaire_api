package fr.formation.Projet_Grp_Java.feignclient;

import org.springframework.stereotype.Component;

@Component
public class ProductFeignFallback implements ProduitFeignClient {
    @Override
    public String getNameById(String id) {
        // Retourne une valeur par défaut en cas d'échec de l'appel
        return "Nom indisponible (fallback)";
    }
}