package com.derteuffel.archive.services;


import com.derteuffel.archive.domain.Secteur;

import java.util.List;

public interface SecteurService {

    Secteur save(Secteur secteur, Long id);
    Secteur update(Secteur secteur, Long id);
    Secteur getOne(Long id);
    boolean delete(Long id);
    List<Secteur> findAll();
    List<Secteur> findAllByDirection(Long id);
    List<Secteur> findAllByVille(String ville);
}
