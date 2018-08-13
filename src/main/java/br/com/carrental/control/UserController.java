package br.com.carrental.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    //Testing
    @GetMapping("/hello")
    @ResponseBody
        public String sayHello(@RequestParam(value = "name", defaultValue = "World") String name){
            return "Hello " + name;
        }

}
