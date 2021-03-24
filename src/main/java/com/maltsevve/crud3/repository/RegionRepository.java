package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.model.Region;

import java.util.List;

public interface RegionRepository extends GenericRepository<Region, Long> {
    @Override
    Region save(Region region);

    @Override
    Region update(Region region);

    @Override
    Region getById(Long aLong);

    @Override
    List<Region> getAll();

    @Override
    void deleteById(Long aLong);
}
