package ru.kduskov.vkapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Photo;
import ru.kduskov.vkapi.repository.AuditRecordRepository;
import ru.kduskov.vkapi.utils.TestData;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {
    private RestTemplate restTemplate;
    @Mock
    protected AuditRecordRepository auditRep;
    private AlbumService albumService;
    private UserService userService;

    {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(ExternalApiConstants.URL));
    }

    @BeforeEach
    public void setUp(){
        albumService = new AlbumService(restTemplate, auditRep);
        userService = new UserService(restTemplate, auditRep);
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    public void shouldReturnAlbumById(int id) throws JsonProcessingException {
        Optional album = albumService.get(id);
        Assertions.assertTrue(album.isPresent());

        Album tAlbum = TestData.getAlbum(id);
        Assertions.assertEquals(tAlbum, album.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    public void shouldReturnAllAlbumsPhotos(int id) throws JsonProcessingException {
        Optional comments = albumService.getPhotos(id);
        Assertions.assertTrue(comments.isPresent());

        List<Photo> expPhotos = TestData.getAlbumsPhotos(id);
        Assertions.assertEquals(expPhotos, comments.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void albumsByUserFilterShouldBeAsListOfUsersAlbums(int userId) throws JsonProcessingException {
        Optional usersAlbums = userService.getAlbums(userId);
        Optional albumsByUser = albumService.getAlbumsByUser(userId);
        Assertions.assertTrue(usersAlbums.isPresent());
        Assertions.assertTrue(albumsByUser.isPresent());
        Assertions.assertEquals(usersAlbums.get(), albumsByUser.get());
    }

    @ParameterizedTest
    @ValueSource(ints = {101})
    public void albumShouldBeCreatedWithNewId(int albumId) throws JsonProcessingException {
        int res = albumService.create(TestData.getAlbum(albumId));
        Assertions.assertEquals(albumId, res);
    }

    @ParameterizedTest
    @ValueSource(ints = {101})
    public void albumShouldBeDeleted(int albumId) throws JsonProcessingException {
        boolean res = albumService.delete(albumId);
        Assertions.assertEquals(true, res);

        // To check was album actually deleted, there should be get request
    }
}
