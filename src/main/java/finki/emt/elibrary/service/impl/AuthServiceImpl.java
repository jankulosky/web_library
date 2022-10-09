package finki.emt.elibrary.service.impl;

import finki.emt.elibrary.model.User;
import finki.emt.elibrary.model.exceptions.InvalidArgumentsException;
import finki.emt.elibrary.model.exceptions.InvalidUserCredentialsException;
import finki.emt.elibrary.repository.UserRepository;
import finki.emt.elibrary.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }
}
