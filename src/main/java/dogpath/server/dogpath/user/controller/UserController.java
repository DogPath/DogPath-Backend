package dogpath.server.dogpath.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/signup")
    public void signUp(){
        return;
    }

    @PostMapping("/login")
    public void login(){
        return;
    }
}
