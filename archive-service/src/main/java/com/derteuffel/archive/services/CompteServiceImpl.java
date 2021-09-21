package com.derteuffel.archive.services;

import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.domain.Role;

import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.repositories.CompteRepository;
import com.derteuffel.archive.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(rollbackFor = {HttpServiceExceptionHandle.class, SQLException.class }, noRollbackFor = EntityNotFoundException.class)
@Slf4j
@RequiredArgsConstructor
public class CompteServiceImpl implements CompteService, UserDetailsService {

   private final CompteRepository compteRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Compte user = compteRepository.findByUsername(username);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), authorities);    }

    @Override
    public Compte saveCompte(Compte compte) {
        log.info("Saving new user {} to the database", compte.getUsername());
        compte.setPassword(passwordEncoder.encode(compte.getPassword()));
        return compteRepository.save(compte);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToCompte(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        Compte compte = compteRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        compte.getRoles().add(role);
    }

    @Override
    public Compte getCompte(String username) {
        log.info("Fetching user {} ", username);

        return compteRepository.findByUsername(username);
    }

    @Override
    public List<Compte> getComptes() {
        log.info("Fetching all users");
        return compteRepository.findAll();
    }


}
