
package com.swipebite.user.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.swipebite.user.entity.User;
import com.swipebite.user.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByMobileNumber(user.getMobileNumber())) {
            throw new RuntimeException("Mobile number already registered");
        }

        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        if (!existingUser.getEmail().equals(updatedUser.getEmail())
                && userRepository.existsByEmail(updatedUser.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (!existingUser.getMobileNumber().equals(updatedUser.getMobileNumber())
                && userRepository.existsByMobileNumber(updatedUser.getMobileNumber())) {
            throw new RuntimeException("Mobile number already registered");
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setMobileNumber(updatedUser.getMobileNumber());

        return userRepository.save(existingUser);
    }
    
    
    @Transactional
    public void deleteUser(Long id) {

        User user = getUserById(id);

        userRepository.delete(user);
    }
}
