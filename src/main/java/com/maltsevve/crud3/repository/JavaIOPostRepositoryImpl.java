package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.SQL.DataBaseConnector;
import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.model.builders.post.ActualPostBuilder;
import com.maltsevve.crud3.model.builders.post.PostDirector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class JavaIOPostRepositoryImpl implements PostRepository {
    private final static Connection CONNECTION = DataBaseConnector.getDataBaseConnector().getConnection();
    private final static PostDirector POST_DIRECTOR = new PostDirector();

    // Проблема со временем, возвращает только дату!!!

    public JavaIOPostRepositoryImpl() {

    }

    @Override
    public Post save(Post post) {
        createTable();
        post.setId(generateID());

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO posts" +
                "(PostID, Content, Created) VALUES (?, ?, ?)")) {
            preparedStatement.setLong(1, post.getId());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = getAll();
        Post post1 = posts.stream().filter((p) -> p.getId().equals(post.getId())).findFirst().orElse(null);

        if (post1 == null) {
            System.out.println("Update is unavailable: no such ID in the data base.");
        } else {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE posts " +
                    "SET Content = ?, Updated = ?" +
                    "WHERE PostID = ?")) {
                preparedStatement.setString(1, post.getContent());
                preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
                preparedStatement.setLong(3, posts.indexOf(post1) + 1);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return post;
    }

    @Override
    public Post getById(Long aLong) {
        Post post = null;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT * FROM posts " +
                "WHERE PostID = ?")) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                POST_DIRECTOR.setPostBuilder(new ActualPostBuilder((resultSet.getString("Content"))));
                post = POST_DIRECTOR.buildPost();
                post.setId(resultSet.getLong("PostID"));
                post.setCreated(resultSet.getTimestamp("Created"));
                post.setUpdated(resultSet.getTimestamp("Updated"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT *
                    FROM posts
                    """);
            while (resultSet.next()) {
                POST_DIRECTOR.setPostBuilder(new ActualPostBuilder((resultSet.getString("Content"))));
                Post post = POST_DIRECTOR.buildPost();
                post.setId(resultSet.getLong("PostID"));
                post.setCreated(resultSet.getTimestamp("Created"));
                post.setUpdated(resultSet.getTimestamp("Updated"));
                posts.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return posts;
    }

    @Override
    public void deleteById(Long aLong) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("DELETE FROM posts " +
                "WHERE PostID = ?")) {
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
                    SELECT PostID
                    FROM Posts
                    """);
            while (resultSet.next()) {
                id.add(resultSet.getLong("PostID"));
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
                    CREATE TABLE IF NOT EXISTS Posts
                                        (
                                        PostID int,
                                        Content varchar(255),
                                        Created date,
                                        Updated date
                                        )
                    """);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
