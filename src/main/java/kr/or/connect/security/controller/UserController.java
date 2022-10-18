package kr.or.connect.security.controller;

import kr.or.connect.security.dto.User;
import kr.or.connect.security.service.UserDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(path = "/members")
@RequiredArgsConstructor
public class UserController {
    private final UserDbService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/loginform")
    public String loginform(){
        return "members/loginform";
    }

    @RequestMapping("/loginerror")
    public String loginerror(@RequestParam("login_error")String loginError){
        return "members/loginerror";
    }

    @GetMapping("/joinform")
    public String joinform(){
        return "members/joinform";
    }

    // 사용자가 입력한 name, email, password가 member에 저장된다.
    @PostMapping("/join")
    public String join(@ModelAttribute User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user, false);
        return "redirect:/members/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "members/welcome";
    }

    // 사용자가 로그인을 한 상태라면, 스프링 MVC는 컨트롤러 메소드에
    // 회원정보를 저장하고 있는 Principal객체를 파라미터로 받을 수 있습니다.
    @GetMapping("/memberinfo")
    public String memberInfo(Principal principal, ModelMap modelMap){
        String loginId = principal.getName();
        User user = userService.getUserByEmail(loginId);
        modelMap.addAttribute("user", user);

        return "members/memberinfo";
    }

}