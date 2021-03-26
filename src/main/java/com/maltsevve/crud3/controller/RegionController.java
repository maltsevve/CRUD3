package com.maltsevve.crud3.controller;

import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.service.RegionService;

import java.util.List;

public class RegionController {
    RegionService regionService = new RegionService();

    public Region save(Region region) {
        return regionService.save(region);
    }

    public Region update(Region region) {
        return regionService.update(region);
    }

    public Region getById(Long aLong) {
        return regionService.getById(aLong);
    }

    public List<Region> getAll() {
        return regionService.getAll();
    }

    public void deleteById(Long aLong) {
        regionService.deleteById(aLong);
    }
}
