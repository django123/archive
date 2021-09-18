package com.derteuffel.archive.controllers;

import com.derteuffel.archive.domain.*;
import com.derteuffel.archive.enums.ERole;
import com.derteuffel.archive.helpers.CountryDetails;
import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.helpers.utils.HttpErrorCodes;
import com.derteuffel.archive.helpers.utils.HttpErrorMessage;
import com.derteuffel.archive.repositories.ArchiveRepository;
import com.derteuffel.archive.repositories.CompteRepository;
import com.derteuffel.archive.repositories.RoleRepository;
import com.derteuffel.archive.repositories.UserRepository;
import com.derteuffel.archive.services.CompteService;
import com.derteuffel.archive.services.DirectionService;
import com.derteuffel.archive.services.Multipart;
import com.derteuffel.archive.services.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/secteur")
@CrossOrigin(origins = "*")
public class SecteurController {

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${back-end-base}")
    private String backeEndBase;

    CountryDetails countryDetails = new CountryDetails();

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private Multipart multipart;



    @GetMapping("/lists")
    public ResponseEntity<?> getSecteurs(HttpServletRequest request){
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<>(secteurService.findAll(), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }


    @PostMapping("/create")
    public ResponseEntity<?>createSecteur(@RequestBody Secteur secteur, @PathVariable Long id){
        try {

            if(secteur == null) throw  new HttpServiceExceptionHandle("Contenue vide !!", HttpErrorCodes.BAD_FORMAT_DATA);
            return new ResponseEntity<>(secteurService.save(secteur, id), HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }

    /** modifier un secteur  **/
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateSecteur(@RequestBody Secteur secteur, @PathVariable Long id){
        try {

            if(secteur == null) throw  new HttpServiceExceptionHandle("Contenue vide !!", HttpErrorCodes.BAD_FORMAT_DATA);
            return new ResponseEntity<>(secteurService.update(secteur, id), HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteSecteur(HttpServletRequest request, @PathVariable Long id){
        try{
            return new ResponseEntity<> (secteurService.delete(id), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }






    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, HttpServletRequest request, Model model) {
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<> (secteurService.getOne(id), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }



    @GetMapping("/lists/{id}")
    public ResponseEntity<?> findAllByDirection(@PathVariable Long id, HttpServletRequest request) {
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<> (secteurService.findAllByDirection(id), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

    @GetMapping("/ville/lists/{ville}")
    public ResponseEntity<?> findAllByVille(@PathVariable String ville, Model model, HttpServletRequest request) {
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<> (secteurService.findAllByVille(ville), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

    @GetMapping("/archive/lists/{id}")
    public ResponseEntity<?> archiveSearchByAge( HttpServletRequest request,@PathVariable Long id){

        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            Role role = roleRepository.findByName(ERole.ROLE_USER.toString());
            List<Archive> lists = new ArrayList<>();

            if (compte.getRoles().contains(role)){
                lists.addAll(archiveRepository.findAllByCompte_Id(compte.getId()));
            }else {
                lists.addAll(archiveRepository.findAllBySecteur_IdOrderByIdDesc(id));
            }
            Secteur secteur = secteurService.getOne(id);
            return new ResponseEntity<>(lists,HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }


}
