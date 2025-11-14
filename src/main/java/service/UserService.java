package service;

import model.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import java.util.Optional;

public class UserService {
    private UserRepository userRepository = new UserRepositoryImpl();

    public boolean registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }

        String hashedPassword = PasswordService.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return true;
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            String storedHash = user.get().getPassword();
            if (PasswordService.checkPassword(password, storedHash)) {
                return user;
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}