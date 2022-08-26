package RestfulApi.restfulservice.basicRest;

import RestfulApi.restfulservice.basicRest.RestTestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class TestController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/test-page")
    public String restTest() {
        return "Hi this is Rest Test Page";
    }

    @GetMapping(path = "/test-page-bean")
    public RestTestBean restTestBean() {

        return new RestTestBean("this is test page");
    }

    @GetMapping(path = "/test-page-bean/path-variable/{name}")
    public RestTestBean restTestBean(@PathVariable(value = "name") String name) {

        return new RestTestBean(String.format("this is test page. 반갑습니다. %s", name));
    }

    @GetMapping(path = "/test-page-internationalized")
    public String testPageInternationalized(
            @RequestHeader(name="Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
