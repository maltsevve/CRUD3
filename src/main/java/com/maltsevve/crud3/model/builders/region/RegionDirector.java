package com.maltsevve.crud3.model.builders.region;

import com.maltsevve.crud3.model.Region;

public class RegionDirector {
    RegionBuilder regionBuilder;

    public void setRegionBuilder(RegionBuilder regionBuilder) {
        this.regionBuilder = regionBuilder;
    }

    public Region buildRegion() {
        regionBuilder.buildRegion();
        regionBuilder.buildName();
        return regionBuilder.getRegion();
    }
}
