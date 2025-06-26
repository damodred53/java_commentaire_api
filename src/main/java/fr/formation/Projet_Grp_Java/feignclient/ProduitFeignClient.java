package fr.formation.Projet_Grp_Java.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-produit", fallback = ProductFeignFallback.class)
public interface ProduitFeignClient {
    @GetMapping("/api/produit/{id}/get-name")
    String getNameById(@PathVariable("id") String id);
}