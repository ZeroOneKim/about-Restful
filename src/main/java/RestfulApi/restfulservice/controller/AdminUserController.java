package RestfulApi.restfulservice.controller;

import RestfulApi.restfulservice.entity.User;
import RestfulApi.restfulservice.entity.UserV2;
import RestfulApi.restfulservice.service.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public MappingJacksonValue AllUsers() {
        List<User> users = userService.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue MJV = new MappingJacksonValue(users);
        MJV.setFilters(filters);

        return MJV;
    }

    //@GetMapping("/v1/users/{id}")
    //@GetMapping(value = "/users/{id}", params = "version=1")
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue OneUserV1(@PathVariable int id) {
        User user = userService.findOne(id);
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue MJV = new MappingJacksonValue(user);
        MJV.setFilters(filters);
        return MJV;
    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}", params = "version=2") http://localhost:8088/admin/users/1/?version=2
    //@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")  at : POSTMAN HEADER Key:Value
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json") // Key=Accept / value=application/vnd.company.appv2+json
    public MappingJacksonValue OneUserV2(@PathVariable int id) {
        User user = userService.findOne(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue MJV = new MappingJacksonValue(userV2);
        MJV.setFilters(filters);
        return MJV;
    }
}
