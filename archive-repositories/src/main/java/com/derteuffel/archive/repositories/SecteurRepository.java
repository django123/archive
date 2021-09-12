package com.derteuffel.archive.repositories;

import com.derteuffel.archive.domain.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long>{

    List<Secteur> findAllByDirection_IdOrderByIdDesc(Long id);
    List<Secteur> findAllByVilleOrderByNameAsc(String ville);
}
