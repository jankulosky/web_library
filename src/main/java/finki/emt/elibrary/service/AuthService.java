package finki.emt.elibrary.service;

import finki.emt.elibrary.model.User;

public interface AuthService {

    User login(String username, String password);
}
