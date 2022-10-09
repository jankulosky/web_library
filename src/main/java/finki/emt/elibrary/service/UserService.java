package finki.emt.elibrary.service;

import finki.emt.elibrary.model.User;
import finki.emt.elibrary.model.enumerations.Role;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
