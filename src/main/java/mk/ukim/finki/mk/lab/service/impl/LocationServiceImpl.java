package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.Location;
import mk.ukim.finki.mk.lab.repository.LocationRepository;
import mk.ukim.finki.mk.lab.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id){
        return locationRepository.findById(id);
    }
}
