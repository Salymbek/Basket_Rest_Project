package peaksoft.api;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.TokenResponse;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;

import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest request){
        return userService.saveUser(request);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid UserRequest request) {
        return userService.authenticate(request);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<AllUserResponse> getAll(){
        return userService.getAllUser();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request){
        return userService.updateUserById(id,request);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return userService.deleteById(id);
    }


}