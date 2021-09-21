package com.derteuffel.archive.controllers.api;

import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.domain.Role;
import com.derteuffel.archive.domain.dto.RoleToUserForm;
import com.derteuffel.archive.services.CompteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final CompteService  compteService;


    @GetMapping("/comptes")
    public ResponseEntity<List<Compte>>getUsers(){
        return ResponseEntity.ok().body(compteService.getComptes());
    }

    @PostMapping("/compte/save")
    public ResponseEntity<Compte>createUser(@RequestBody Compte user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/compte/save").toUriString());
        return ResponseEntity.created(uri).body(compteService.saveCompte(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(compteService.saveRole(role));
    }


    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm roleToUserForm){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        compteService.addRoleToCompte(roleToUserForm.getUsername(),roleToUserForm.getRoleName());
        return ResponseEntity.created(uri).build();
    }
}
