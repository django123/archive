package com.derteuffel.archive.services;

import com.derteuffel.archive.domain.Direction;
import com.derteuffel.archive.domain.Secteur;
import com.derteuffel.archive.helpers.exceptions.EntityNotFoundException;
import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.helpers.utils.HttpErrorCodes;
import com.derteuffel.archive.repositories.DirectionRepository;
import com.derteuffel.archive.repositories.SecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = {HttpServiceExceptionHandle.class, SQLException.class }, noRollbackFor = EntityNotFoundException.class)
public class SecteurServiceImpl implements SecteurService {

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private SecteurRepository secteurRepository;


    @Override
    public Secteur save(Secteur secteur, Long id) {

        try {
            if(secteur == null)throw new HttpServiceExceptionHandle("le secteur envoyée est vide", HttpErrorCodes.INTERNAL_SERVER_ERROR);
            Direction direction = directionRepository.getOne(id);
            secteur.setDirection(direction);
            return secteurRepository.save(secteur);
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }

    }

    @Override
    public Secteur update(Secteur secteur, Long id) {
        try {
            Secteur existedSecteur = secteurRepository.getOne(id);
            existedSecteur.setLocation(secteur.getLocation());
            existedSecteur.setName(secteur.getName());
            existedSecteur.setVille(secteur.getVille());
            return secteurRepository.save(existedSecteur);

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }

    }

    @Override
    public Secteur getOne(Long id) {

        try{
            return secteurRepository.getOne(id);

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public boolean delete(Long id) {
        try{
            Secteur secteur = secteurRepository.findById(id).get();
            if(secteur == null) throw new HttpServiceExceptionHandle("Client introuvable",HttpErrorCodes.INTERNAL_SERVER_ERROR);

            secteurRepository.deleteById(id);
            return true;
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }


    }

    @Override
    public List<Secteur> findAll() {

        try{
            return secteurRepository.findAll();

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public List<Secteur> findAllByDirection(Long id) {
        try {
            return secteurRepository.findAllByDirection_IdOrderByIdDesc(id);

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public List<Secteur> findAllByVille(String ville) {
        try {
            return secteurRepository.findAllByVilleOrderByNameAsc(ville);

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }
}
