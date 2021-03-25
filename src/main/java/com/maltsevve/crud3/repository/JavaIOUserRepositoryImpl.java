package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.SQL.DataBaseConnector;
import com.maltsevve.crud3.model.Role;
import com.maltsevve.crud3.model.User;
import com.maltsevve.crud3.model.builders.user.ActualUserBuilder;
import com.maltsevve.crud3.model.builders.user.UserDirector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavaIOUserRepositoryImpl implements UserRepository {
    private final static Connection CONNECTION = DataBaseConnector.getDataBaseConnector().getConnection();
    private final static UserDirector USER_DIRECTOR = new UserDirector();

    JavaIOPostRepositoryImpl jpr = new JavaIOPostRepositoryImpl();
    JavaIORegionRepositoryImpl jrr = new JavaIORegionRepositoryImpl();

    public JavaIOUserRepositoryImpl() {

    }

    @Override
    public User save(User user) {
        createTable();
        user.setId(generateID());

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO users" +
                "(UserID, FirstName, LastName, Region, Role) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setLong(4, user.getRegion().getId());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.executeUpdate();

            jrr.save(user.getRegion());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public User update(User user) {
        List<User> users = getAll();
        User user1 = users.stream().filter((u) -> u.getId().equals(user.getId())).findFirst().orElse(null);

        if (user1 == null) {
            System.out.println("Update is unavailable: no such ID in the data base.");
            return user;
        } else {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE users " +
                    "SET FirstName = ?, LastName = ?, Region = ?, Role = ? " +
                    "WHERE UserID = ?")) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setLong(3, user.getRegion().getId());
                preparedStatement.setString(4, String.valueOf(user.getRole()));
                preparedStatement.setLong(5, users.indexOf(user1) + 1);
                preparedStatement.executeUpdate();

                jrr.save(user.getRegion());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return user1;
        }
    }

    @Override
    public User getById(Long aLong) {
        User user = null;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT * FROM users " +
                "WHERE UserID = ?")) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                USER_DIRECTOR.setUserBuilder(new ActualUserBuilder(resultSet.getString("FirstName"),
                        resultSet.getString("LastName"), jpr.getAll(),
                        jrr.getById(resultSet.getLong("Region")),
                        Role.valueOf(resultSet.getString("Role"))));
                user = USER_DIRECTOR.buildUser();
                user.setId(resultSet.getLong("UserID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT *
                    FROM users
                    """);
            while (resultSet.next()) {
                USER_DIRECTOR.setUserBuilder(new ActualUserBuilder(resultSet.getString("FirstName"),
                        resultSet.getString("LastName"), jpr.getAll(),
                        jrr.getById(resultSet.getLong("Region")),
                        Role.valueOf(resultSet.getString("Role"))));
                User user = USER_DIRECTOR.buildUser();
                user.setId(resultSet.getLong("UserID"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    @Override
    public void deleteById(Long aLong) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("DELETE FROM users " +
                "WHERE UserID = ?")) {
            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Long generateID() {
        List<Long> id = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT UserID
                    FROM users
                    """);
            while (resultSet.next()) {
                id.add(resultSet.getLong("UserID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (!id.isEmpty()) {
            return Objects.requireNonNull(id.stream().max(Long::compare).orElse(null)) + 1;
        } else {
            return 1L;
        }
    }

    private void createTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS Users
                                        (
                                        UserID int              NOT NULL UNIQUE,
                                        FirstName varchar(255),
                                        LastName varchar(255),
                                        Region int              NOT NULL,
                                        Role varchar(255)       NOT NULL 
                                        )
                    """);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
