package co.chaiwatm.demo.controllers;

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
        me.setId(1000);
        me.setName("Chaiwat Matarak");

        GreetingResponse response = new GreetingResponse();
        response.setResponseUid("9c07234b-90cb-4c15-a1b6-d277ddda8aca");
        response.setUser(me);

        return ResponseEntity.ok(response);
    }
}