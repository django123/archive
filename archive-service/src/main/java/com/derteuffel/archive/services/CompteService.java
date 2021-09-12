package com.derteuffel.archive.services;


import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.helpers.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface CompteService extends UserDetailsService {

    Compte findByUsernameOrEmail(String username, String email);
    Compte save(UserDto userDto);

    List<Compte> findAllBySecteur(Long id);
    List<Compte> findAllByDIrection(Long id);
}
