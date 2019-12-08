package co.chaiwatm.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import co.chaiwatm.demo.models.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<GenericResponse> user() {
        List<User> users = GetUsers();
        User me = users.get(0);

        GenericResponse response = new GenericResponse<User>();
        response.setResponseUid("9c07234b-90cb-4c15-a1b6-d277ddda8aca");
        response.setData(me);

        HttpHeaders headers = GetResponseHeader(response);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<GenericResponse> users() {
        List<User> users = GetUsers();

        GenericResponse response = new GenericResponse<List<User>>();
        response.setResponseUid("9c07234b-90cb-4c15-a1b6-d277ddda8aca");
        response.setData(users);

        HttpHeaders headers = GetResponseHeader(response);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    private List<User> GetUsers() {
        List<User> users = new ArrayList<User>();

        User user1 = new User();
        user1.setId(1001);
        user1.setName("Chaiwat Matarak");

        User user2 = new User();
        user2.setId(1002);
        user2.setName("John Doe");

        users.add(user1);
        users.add(user2);

        return users;
    }

    private HttpHeaders GetResponseHeader(GenericResponse response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(response);
        String xSignature = GetSignature(json);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Signature", xSignature);

        return headers;
    }

    private String GetSignature(String text) {
        String secret = "8a64c35116e906b79d0763d0354b8a5e7ad31515c0774e8cb9a3e2034fc0219f";
        // byte[] byteKey = secret.getBytes();
        byte[] byteKey = hexStringToByteArray(secret);
        Key key = new SecretKeySpec(byteKey, "HMACSHA512");

        try {
            Mac hmacSha512 = Mac.getInstance("HMACSHA512");
            hmacSha512.init(key);
            byte[] result = hmacSha512.doFinal(text.getBytes());

            return toHexString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }
}