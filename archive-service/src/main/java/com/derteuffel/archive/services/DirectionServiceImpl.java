package com.derteuffel.archive.services;


import com.derteuffel.archive.domain.Direction;
import com.derteuffel.archive.domain.Secteur;
import com.derteuffel.archive.helpers.exceptions.EntityNotFoundException;
import com.derteuffel.archive.helpers.exceptions.HttpServiceExceptionHandle;
import com.derteuffel.archive.helpers.utils.HttpErrorCodes;
import com.derteuffel.archive.repositories.DirectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = {HttpServiceExceptionHandle.class, SQLException.class }, noRollbackFor = EntityNotFoundException.class)
public class DirectionServiceImpl implements DirectionService {

    @Autowired
    private DirectionRepository directionRepository;


    @Override
    public Direction save(Direction direction) {
        try {
            if (direction == null)throw new HttpServiceExceptionHandle("la direction envoyée est vide", HttpErrorCodes.INTERNAL_SERVER_ERROR);
            return directionRepository.save(direction);
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }

    }

    @Override
    public Direction update(Direction direction, Long id) {

        try {
            Direction existedDirection = directionRepository.getOne(id);
            existedDirection.setLocation(direction.getLocation());
            existedDirection.setName(direction.getName());
            existedDirection.setRegion(direction.getRegion());
            existedDirection.setVille(direction.getVille());
            return directionRepository.save(existedDirection);

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }

    }

    @Override
    public Direction getOne(Long id) {
        try {
            return directionRepository.findById(id).get();

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Direction direction = directionRepository.findById(id).get();
            if (direction == null)throw new HttpServiceExceptionHandle("Client introuvable",HttpErrorCodes.INTERNAL_SERVER_ERROR);
            directionRepository.deleteById(id);
           return true;
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public List<Direction> findAll() {
        try{
            return directionRepository.findAll();

        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }

    }

    @Override
    public List<Direction> findAllByRegion(String region) {
        try{
            return directionRepository.findAllByVilleOrderByNameAsc(region);
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }

    @Override
    public List<Direction> findAllByVille(String ville) {
        try{
            return directionRepository.findAllByVilleOrderByNameAsc(ville);
        }catch (HttpServiceExceptionHandle e) {
            HttpErrorCodes code = (e.getErrorCode() != null ? HttpErrorCodes.fromId(e.getErrorCode()) : HttpErrorCodes.INTERNAL_SERVER_ERROR);
            throw new HttpServiceExceptionHandle(e.getMessage(),code);
        }
    }
}
