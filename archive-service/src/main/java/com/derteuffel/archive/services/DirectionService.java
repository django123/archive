package com.derteuffel.archive.services;


import com.derteuffel.archive.domain.Direction;

import java.util.List;

public interface DirectionService {

    Direction save(Direction direction);
    Direction update(Direction direction, Long id);
    Direction getOne(Long id);
    boolean delete(Long id);
    List<Direction> findAll();
    List<Direction> findAllByRegion(String region);
    List<Direction> findAllByVille(String ville);
}
