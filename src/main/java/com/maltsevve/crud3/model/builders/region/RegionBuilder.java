package com.maltsevve.crud3.model.builders.region;

import com.maltsevve.crud3.model.Region;

public abstract class RegionBuilder {
    Region region;

    public void buildRegion() {
        region = new Region();
    }

    public void buildName() {

    }

    public Region getRegion() {
        return region;
    }
}
