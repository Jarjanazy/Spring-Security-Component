package codeit.security.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @GetMapping("/test")
    public String test()
    {
        return "You've just authenticated !!";
    }
}
