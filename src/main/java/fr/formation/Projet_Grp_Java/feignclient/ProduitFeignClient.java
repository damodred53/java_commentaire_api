package fr.formation.Projet_Grp_Java.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produit-service", url = "http://localhost:8080/api/produit")
public interface ProduitFeignClient {

    @GetMapping("/{id}/get-name")
    String getNameById(@PathVariable("id") String id);
}