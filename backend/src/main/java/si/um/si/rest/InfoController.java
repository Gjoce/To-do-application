package si.um.si.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("api/hello")
    public String hello() {
        return "Hello World";
    }
}
