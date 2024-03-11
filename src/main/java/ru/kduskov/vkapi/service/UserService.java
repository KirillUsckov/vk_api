package ru.kduskov.vkapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.kduskov.vkapi.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;

@Service
public class UserService {
    public Optional<User> getUser(int id) {
        Optional optional = Optional.empty();
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/users/" + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int code = con.getResponseCode();
            if(code == 200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String json = content.toString();
                User user = objectMapper.readValue(json, User.class);
                in.close();
                optional = Optional.of(user);
            }
            con.disconnect();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return optional;
    }
}
