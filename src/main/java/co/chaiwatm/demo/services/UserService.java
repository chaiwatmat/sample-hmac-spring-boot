package co.chaiwatm.demo.services;

import java.util.ArrayList;
import java.util.List;

import co.chaiwatm.demo.models.User;
import lombok.*;

@Data
public class UserService {

    public List<User> list() {
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

}