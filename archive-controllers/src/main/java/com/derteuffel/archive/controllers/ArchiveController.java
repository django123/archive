package com.derteuffel.archive.controllers;


import com.derteuffel.archive.domain.Archive;
import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.domain.Role;
import com.derteuffel.archive.helpers.CountryDetails;
import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.helpers.utils.HttpErrorMessage;
import com.derteuffel.archive.repositories.ArchiveRepository;
import com.derteuffel.archive.repositories.CompteRepository;
import com.derteuffel.archive.repositories.RoleRepository;
import com.derteuffel.archive.repositories.UserRepository;
import com.derteuffel.archive.services.CompteService;
import com.derteuffel.archive.services.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/archives")
@CrossOrigin(origins = "*")
public class ArchiveController {
    @Autowired
    private CompteService compteService;
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private Multipart multipart;

    private final

    CountryDetails countryDetails = new CountryDetails();


    @GetMapping("/home")
    public ResponseEntity<?> home(HttpServletRequest request){
        try {
            List<Archive> lists = archiveRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            Role role = roleRepository.findByName("ROLE_USER");
            return new ResponseEntity<>(lists, HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }



    }

    @GetMapping("/lists")
    public ResponseEntity<?> archives(HttpServletRequest request){

        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            List<Archive> lists = archiveRepository.findAllByCompte_Id(compte.getId());
            return new ResponseEntity<>(lists, HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }



    }

    @GetMapping("/lists/nature/{natureDocument}")
    public  ResponseEntity<?> archiveSearchByNature( HttpServletRequest request,@PathVariable String natureDocument){
        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            List<Archive> lists = archiveRepository.findAllByNatureDocument(natureDocument,Sort.by(Sort.Direction.DESC,"id"));

            return new ResponseEntity<>(lists, HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }

    @GetMapping("/lists/age/{age}")
    public ResponseEntity<?> archiveSearchByAge(Model model, HttpServletRequest request,@PathVariable String age){
        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            List<Archive> lists = archiveRepository.findAllByAge(age,Sort.by(Sort.Direction.DESC,"id"));
            return new ResponseEntity<>(lists, HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));

        }

    }



    @PostMapping("/save")
    public ResponseEntity<?> archiveSave(Archive archive, RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam("files") MultipartFile[] files){

        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            if (files.length != 0){
                for (MultipartFile file : files){
                    multipart.store(file);
                    if (archive.getPieces().size() == 0){
                        archive.setPieces(new ArrayList<String>(Collections.singleton("/downloadFile/" + file.getOriginalFilename())));
                    }else {
                        archive.getPieces().add("/downloadFile/"+file.getOriginalFilename());
                    }
                }
            }

            archive.setCode("#"+archive.getNatureDocument().substring(0,2).toUpperCase()+"/"+archive.getService().substring(0,3).toUpperCase()+"/"+archiveRepository.findAll().size());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            archive.setDateEnregistrement(sdf.format(date));
            archive.setCompte(compte);

            redirectAttributes.addFlashAttribute("success","Operation reussie");
            return new ResponseEntity<>(archiveRepository.save(archive), HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));

        }

    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> archiveDetail(@PathVariable Long id, HttpServletRequest request){
        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            Archive archive = archiveRepository.getOne(id);
            return new ResponseEntity<>(archive, HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));

        }
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<?> archiveUpdate(@PathVariable Long id,HttpServletRequest request){
        try {
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            Archive archive = archiveRepository.getOne(id);
            return new ResponseEntity<>(archive, HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e){
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));

        }

    }

    @GetMapping("/delete/{id}")
    public void archiveDelete(@PathVariable Long id, RedirectAttributes redirectAttributes){
            Archive archive = archiveRepository.getOne(id);
            redirectAttributes.addFlashAttribute("success","Suppression reussie");
            archiveRepository.delete(archive);

    }
}
