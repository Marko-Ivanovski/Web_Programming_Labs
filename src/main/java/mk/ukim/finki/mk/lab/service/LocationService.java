package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.Location;

import java.util.List;

public interface LocationService {
    List<Location> findAll();

    Location findById(Long id);
}