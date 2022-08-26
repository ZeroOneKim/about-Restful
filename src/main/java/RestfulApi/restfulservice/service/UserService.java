package RestfulApi.restfulservice.service;

import RestfulApi.restfulservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    private static List<User> users = new ArrayList<>();
    private static Integer usersNumber = 3;

    static {
        users.add(new User(1, "김유저1", new Date(), "password1", "970124-1234567"));
        users.add(new User(2, "황유저2", new Date(), "password2", "123456-1231231"));
        users.add(new User(3, "박유저3", new Date(), "password3", "111111-0000000"));
    }
    public List<User> findAll() {
        return users;
    }
    public User findOne(int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    public User save(User user) {
        if(user.getId() == null) {
            user.setId(usersNumber+1);
            usersNumber++;
        }
        users.add(user);
        return user;
    }

    public User deleteUser(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
