package com.derteuffel.archive.controllers;


import com.derteuffel.archive.domain.Compte;
import com.derteuffel.archive.domain.Direction;
import com.derteuffel.archive.helpers.CountryDetails;
import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.helpers.utils.HttpErrorCodes;
import com.derteuffel.archive.helpers.utils.HttpErrorMessage;
import com.derteuffel.archive.services.CompteService;
import com.derteuffel.archive.services.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/direction")
@CrossOrigin(origins = "*")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private CompteService compteService;

    CountryDetails countryDetails = new CountryDetails();

    @GetMapping("/lists")
    public ResponseEntity<?> getDirections(HttpServletRequest request){
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<>(directionService.findAll(), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }


    @PostMapping("/create")
    public ResponseEntity<?>createDirection(@RequestBody Direction direction){
        try {

            if(direction == null) throw  new HttpServiceExceptionHandle("Contenue vide !!", HttpErrorCodes.BAD_FORMAT_DATA);
            return new ResponseEntity<>(directionService.save(direction), HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }

    /** modifier un direction  **/
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateDirection(@RequestBody Direction direction,@PathVariable Long id){
        try {

            if(direction == null) throw  new HttpServiceExceptionHandle("Contenue vide !!", HttpErrorCodes.BAD_FORMAT_DATA);
            return new ResponseEntity<>(directionService.update(direction, id), HttpStatus.OK);

        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteDirection(HttpServletRequest request, @PathVariable Long id){
        try{
            return new ResponseEntity<> (directionService.delete(id), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?>  getDirection(HttpServletRequest request,@PathVariable("id") Long id){
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<>(directionService.getOne(id), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }


    }

    @GetMapping("/ville/lists/{ville}")
    public ResponseEntity<?>findAllByVille(@PathVariable String ville, HttpServletRequest request){
        try{
            Principal principal = request.getUserPrincipal();
            Compte compte = compteService.findByUsernameOrEmail(principal.getName(),principal.getName());
            return new ResponseEntity<>(directionService.findAllByVille(ville), HttpStatus.OK);
        }catch (HttpServiceExceptionHandle e) {
            return new ResponseEntity<>(new HttpErrorMessage(new Date(), e.getMessage()), HttpStatus.valueOf(e.getErrorCode()));
        }
    }

}
