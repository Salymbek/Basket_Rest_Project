package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.TokenResponse;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.AllUserResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.enums.Role;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.User;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;


    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(encoder.encode("admin"));
        user.setRole(Role.ADMIN);
        user.setCreatedDate(ZonedDateTime.now());
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            userRepository.save(user);
        }
    }

    @Override
    public TokenResponse authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(),
                        userRequest.password()
                )
        );

        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new NotFoundException(String.format
                        ("User with email: %s doesn't exists", userRequest.email())));
        String token = jwtService.generateToken(user);

        return TokenResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }

    @Override
    public SimpleResponse saveUser(UserRequest request) {


        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistException(String.format(
                    "User with email: %s is exists", request.email()
            ));
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setCreatedDate(ZonedDateTime.now());
        user.setUpdateDate(null);
        user.setRole(Role.USER);


        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("User with name: "+request.firstName()+" is successfully saved!!!")
                .build();
    }

    @Override
    public List<AllUserResponse> getAllUser() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findByUserId(id).orElseThrow(
                ()-> new NotFoundException("User with id: "+id+" not found!"));
    }

    @Override
    public SimpleResponse updateUserById(Long id, UserRequest request) {
        for (User user : userRepository.findAll()) {
            if (!user.getId().equals(id) && user.getEmail().equals(request.email())) {
                throw new NotFoundException(String.format(
                        "User with login: %s is exists", request.email()
                ));
            }
        }
        User user = userRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("User with id: "+id+" not found!")
        );

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setUpdateDate(ZonedDateTime.now());
        user.setRole(Role.USER);



        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("User with id: "+id+" is successfully updated!!!")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("User with id - %s is not found!", id))
                    .build();
        }
        userRepository.deleteById(id);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("User with id - %s is deleted!", id)
        );
    }


}
