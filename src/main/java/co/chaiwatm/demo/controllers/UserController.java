package co.chaiwatm.demo.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.chaiwatm.demo.models.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<GreetingResponse> user() {
        User me = new User();
        me.setId(1001);
        me.setName("Chaiwat Matarak");
        // me.setFriend("M");

        GreetingResponse greetingResponse = new GreetingResponse();
        greetingResponse.setResponseUid("9c07234b-90cb-4c15-a1b6-d277ddda8aca");
        greetingResponse.setUser(me);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(greetingResponse);
        System.out.println(json);

        ResponseEntity response = new ResponseEntity<>(greetingResponse, HttpStatus.OK);

        return response;
    }
}