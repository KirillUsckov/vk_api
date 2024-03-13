package ru.kduskov.vkapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kduskov.vkapi.model.external.api.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class TestData {
    public static User getApiUser(int id) throws JsonProcessingException {
        String result = getResource(String.format("users%d.json", id));
        return new ObjectMapper().readValue(result, User.class);
    }

    public static Post getPost(int id) throws JsonProcessingException {
        String result = getResource(String.format("posts%d.json", id));
        return new ObjectMapper().readValue(result, Post.class);
    }

    public static Album getAlbum(int id) throws JsonProcessingException {
        String result = getResource(String.format("albums%d.json", id));
        return new ObjectMapper().readValue(result, Album.class);
    }


    public static List<Album> getApiUserAlbums(int id) throws JsonProcessingException {
        String result = getResource(String.format("usersAlbums%d.json", id));
        return new ObjectMapper().readValue(result, new TypeReference<List<Album>>() {
        });
    }

    public static List<Post> getApiUserPosts(int id) throws JsonProcessingException {
        String result = getResource(String.format("usersPosts%d.json", id));
        return new ObjectMapper().readValue(result, new TypeReference<List<Post>>() {
        });
    }

    public static List<Comment> getPostsComments(int id) throws JsonProcessingException {
        String result = getResource(String.format("postsComments%d.json", id));
        return new ObjectMapper().readValue(result, new TypeReference<List<Comment>>() {
        });
    }

    public static List<Photo> getAlbumsPhotos(int id) throws JsonProcessingException {
        String result = getResource(String.format("albumsPhotos%d.json", id));
        return new ObjectMapper().readValue(result, new TypeReference<List<Photo>>() {
        });
    }

    private static String getResource(String filename) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));
    }
}
