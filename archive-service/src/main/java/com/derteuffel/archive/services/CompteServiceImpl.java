package com.derteuffel.archive.services;

import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.domain.Role;
import com.derteuffel.archive.domain.Secteur;
import com.derteuffel.archive.domain.User;
import com.derteuffel.archive.helpers.UserDto;
import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.helpers.utils.HttpErrorCodes;
import com.derteuffel.archive.repositories.CompteRepository;
import com.derteuffel.archive.repositories.RoleRepository;
import com.derteuffel.archive.repositories.SecteurRepository;
import com.derteuffel.archive.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = {HttpServiceExceptionHandle.class, SQLException.class }, noRollbackFor = EntityNotFoundException.class)
public class CompteServiceImpl implements CompteService {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecteurRepository secteurRepository;


    @Override
    public Compte findByUsernameOrEmail(String username, String email) {
        return compteRepository.findByUsernameOrEmail(username,email);
    }

    @Override
    public Compte save(UserDto userDto) {
        Compte compte = new Compte();
        User user = new User();
        Secteur secteur = secteurRepository.getOne(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstname());
        user.setFonction(userDto.getFonction());
        user.setLastname(userDto.getLastname());
        user.setMatricule(userDto.getMatricule());
        user.setSexe(userDto.getSexe());
        user.setTelephone(userDto.getTelephone());
        compte.setSecteur(secteur);
        userRepository.save(user);
        compte.setEmail(userDto.getEmail());
        compte.setUser(user);
        compte.setPassword(passwordEncoder.encode("0000"));
        compte.setCode("0000");
        compte.setUsername(userDto.getFirstname()+(userRepository.findAll().size()+1));
        Role role = new Role();
        if (compteRepository.findAll().size()<=2){
            role.setName("ROLE_ROOT");
        }else {
            role.setName("ROLE_USER");
        }
        Role existRole =  roleRepository.findByName(role.getName());
        if (existRole!=null){
            compte.setRoles(Arrays.asList(existRole));
        }else {
            System.out.println(role.getName());
            roleRepository.save(role);
            compte.setRoles(Arrays.asList(role));
        }

        compteRepository.save(compte);
        return compte;
    }

    @Override
    public List<Compte> findAllBySecteur(Long id) {

        try{
            return compteRepository.findAllBySecteur_IdOrderByIdDesc(id);
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }

    }

    @Override
    public List<Compte> findAllByDIrection(Long id) {
        try{
            return compteRepository.findAllByDirection_IdOrderByIdDesc(id);

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(s);
        Compte compte = compteRepository.findByUsernameOrEmail(s,s);
        if (compte == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(compte.getUsername(),
                compte.getPassword(),
                mapRolesToAuthorities(compte.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
