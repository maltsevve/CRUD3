package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.SQL.DataBaseConnector;
import com.maltsevve.crud3.model.Region;
import com.maltsevve.crud3.model.builders.region.ActualRegionBuilder;
import com.maltsevve.crud3.model.builders.region.RegionDirector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIORegionRepositoryImpl implements RegionRepository {
    private final static Connection CONNECTION = DataBaseConnector.getDataBaseConnector().getConnection();
    private final static RegionDirector REGION_DIRECTOR = new RegionDirector();

    public JavaIORegionRepositoryImpl() {

    }

    @Override
    public Region save(Region region) {
        List<Region> regions = getAll();
        Region region1 = regions.stream().filter((r) -> r.getName().
                equals(region.getName())).findFirst().orElse(null);

        if (region1 == null) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO regions " +
                    "(Region) VALUES (?)")) {
                preparedStatement.setString(1, region.getName());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return region;
        } else {
            return region1;
        }
    }

    @Override
    public Region update(Region region) {
        List<Region> regions = getAll();
        Region region1 = regions.stream().filter((r) -> r.getId().equals(region.getId())).findFirst().orElse(null);

        if (region1 == null) {
            System.out.println("Update is unavailable: no such ID in the data base.");
            return region;
        } else {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE regions SET Region = ?" +
                    "WHERE RegionID = ?")) {
                preparedStatement.setString(1, region.getName());
                preparedStatement.setLong(2, region.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return region1;
        }
    }

    @Override
    public Region getById(Long aLong) {
        Region region = null;

        if (aLong > 0) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT * FROM regions " +
                    "WHERE RegionID = ?")) {
                preparedStatement.setLong(1, aLong);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    REGION_DIRECTOR.setRegionBuilder(new ActualRegionBuilder((resultSet.getString("Region"))));
                    region = REGION_DIRECTOR.buildRegion();
                    region.setId(resultSet.getLong("RegionID"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return region;
    }

    @Override
    public List<Region> getAll() {
        List<Region> regions = new ArrayList<>();

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT *
                    FROM Regions
                    """);
            while (resultSet.next()) {
                REGION_DIRECTOR.setRegionBuilder(new ActualRegionBuilder(resultSet.getString("Region")));
                Region region = REGION_DIRECTOR.buildRegion();
                region.setId(resultSet.getLong("RegionID"));
                regions.add(region);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return regions.stream().sorted(Comparator.comparing(Region::getId)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long aLong) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("DELETE FROM regions " +
                "WHERE RegionID = ?")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
