package co.chaiwatm.demo.controllers;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import co.chaiwatm.demo.models.*;
import co.chaiwatm.demo.services.UserService;
import co.chaiwatm.demo.utilities.RequestHeaderUtility;
import co.chaiwatm.demo.utilities.SignatureUtility;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<GenericResponse> user(@RequestHeader Map<String, String> requestHeaders) {
        RequestHeaderModel headerModel = RequestHeaderUtility.GetHeaders(requestHeaders);
        List<User> users = userService.list();
        User me = users.get(0);

        GenericResponse response = new GenericResponse<User>();
        response.setResponseUid(headerModel.getRequestuid());
        response.setData(me);

        HttpHeaders headers = SignatureUtility.GetResponseHeader(response);

        return ResponseEntity.ok().headers(headers).body(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> users(@RequestHeader Map<String, String> requestHeaders) {
        RequestHeaderModel headerModel = RequestHeaderUtility.GetHeaders(requestHeaders);
        List<User> users = userService.list();
        GenericResponse response = new GenericResponse<List<User>>();
        response.setResponseUid(headerModel.getRequestuid());
        response.setData(users);

        HttpHeaders headers = SignatureUtility.GetResponseHeader(response);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}