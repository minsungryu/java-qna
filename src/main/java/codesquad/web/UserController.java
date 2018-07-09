package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("id", id);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable int id, User user) {
        if (verifyUser(user.getUserId(), user.getPassword())) {
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    private boolean verifyUser(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password) != null;
    }

}
