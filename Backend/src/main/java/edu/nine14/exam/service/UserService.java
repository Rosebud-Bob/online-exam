package edu.nine14.exam.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.nine14.exam.entity.User;
import edu.nine14.exam.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.ttl}")
    private int expirationTime;
    @Value("${jwt.secret}")
    private String secret;

    public String login(String username, String password) throws FailedLoginException {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty())
            throw new FailedLoginException("User not found");
        if (!user.get().getPassword().equals(password))
            throw new FailedLoginException("Password wrong");
        System.out.println("login: " + username + " " + password);
        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim("username", username)
                .withClaim("user_type", user.get().getUserType())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime * 1000L * 60L))
                .sign(Algorithm.HMAC256(secret));
    }

    public void register(String username, String password, String userType) {
        Optional<User> user_check = userRepository.findById(username);
        if (user_check.isPresent())
            throw new IllegalArgumentException("User already exists");
        User user = new User();
        user.setUsername(username);
        user.setName(username);
        user.setPassword(password);
        user.setUserType(userType);
        userRepository.save(user);
    }

    public void register(User user) {
        userRepository.save(user);
    }

    public Map<String, String> getUserInformation(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty())
            throw new IllegalArgumentException("User not found");
        return user.get().getUserInformation();
    }

    public void updateUserInformation(String username, Map<String, String> userInformation) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty())
            throw new IllegalArgumentException("User not found");
        user.get().updateUserInformation(userInformation);
        userRepository.save(user.get());
    }

    public void removeUser(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty())
            throw new IllegalArgumentException("User not found");
        userRepository.delete(user.get());
    }

    public List<User> getUserList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("userType", "username"));
        return userRepository.findAll(pageable).getContent();
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty())
            throw new IllegalArgumentException("User not found");
        if (!user.get().getPassword().equals(oldPassword))
            throw new IllegalArgumentException("Password wrong");
        user.get().setPassword(newPassword);
        userRepository.save(user.get());
    }
}
