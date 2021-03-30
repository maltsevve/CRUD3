package com.maltsevve.crud3.repository;

import com.maltsevve.crud3.SQL.DataBaseConnector;
import com.maltsevve.crud3.model.Post;
import com.maltsevve.crud3.model.builders.post.ActualPostBuilder;
import com.maltsevve.crud3.model.builders.post.PostDirector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIOPostRepositoryImpl implements PostRepository {
    private final static Connection CONNECTION = DataBaseConnector.getDataBaseConnector().getConnection();
    public final static PostDirector POST_DIRECTOR = new PostDirector();
    private static Long userId;

    public JavaIOPostRepositoryImpl() {

    }

    public static void setUserId(Long userId) {
        JavaIOPostRepositoryImpl.userId = userId;
    }

    @Override
    public Post save(Post post) {
        if (userId != null) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO posts " +
                    "(Content, Created, UserID) " +
                    "VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, post.getContent());
                preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
                preparedStatement.setLong(3, userId);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("Please setup user ID.");
        }

        return post;
    }

    @Override
    public Post update(Post post) {
        List<Post> posts = getAll();
        Post post1 = posts.stream().filter((p) -> p.getId().equals(post.getId())).findFirst().orElse(null);

        if (post1 == null) {
            System.out.println("Update is unavailable: no such ID in the data base.");
            return post;
        } else {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE posts " +
                    "SET Content = ?, Updated = ?, UserID = ? " +
                    "WHERE PostID = ?")) {
                preparedStatement.setString(1, post.getContent());
                preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
                preparedStatement.setLong(3, userId);
                preparedStatement.setLong(4, post.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return post1;
        }
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
                POST_DIRECTOR.setPostBuilder(new ActualPostBuilder(resultSet.getString("Content")));
                Post post = POST_DIRECTOR.buildPost();
                post.setId(resultSet.getLong("PostID"));
                post.setCreated(resultSet.getTimestamp("Created"));
                post.setUpdated(resultSet.getTimestamp("Updated"));
                posts.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return posts.stream().sorted(Comparator.comparing(Post::getId)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long aLong) {
        if (userId != 3) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("DELETE FROM posts " +
                    "WHERE PostID = ?")) {
                preparedStatement.setLong(1, aLong);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.out.println("Only administrators and moderators can delete posts.");
        }
    }
}
