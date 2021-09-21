package com.derteuffel.archive.services;


import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.domain.Role;
import com.derteuffel.archive.helpers.UserDto;

import java.util.List;


public interface CompteService  {


    Compte saveCompte(Compte compte);
    Role saveRole(Role role);
    void addRoleToCompte(String username,String roleName);
    Compte getCompte(String username);
    List<Compte>getComptes();
}
