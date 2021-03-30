package com.maltsevve.service;

import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.service.RegionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegionServiceTest {
    @Mock
    RegionService regionService = mock(RegionService.class);
    Region region = new Region();

    @Test
    public void saveTest() {
        region.setName("Rostov");
        when(regionService.save(region)).thenReturn(region);
    }

    @Test
    public void updateTest() {
        when(regionService.update(region)).thenReturn(region);
    }

    @Test
    public void getByIdTest() {
        region.setId(1L);
        when(regionService.getById(1L)).thenReturn(region);
    }

    @Test
    public void getAllTest() {
        List<Region> regions = new ArrayList<>();
        region.setId(1L);
        regions.add(region);
        when(regionService.getAll()).thenReturn(regions);
    }

    @Test
    public void deleteByIdTest() {
        regionService.deleteById(1L);
        verify(regionService, times(1)).deleteById(1L);
    }
}

