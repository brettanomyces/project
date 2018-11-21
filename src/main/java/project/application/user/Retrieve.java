package project.application.user;

import lombok.AllArgsConstructor;
import lombok.Value;
import project.domain.user.User;
import project.domain.user.UserRepository;

import java.util.Optional;

@AllArgsConstructor
public class Retrieve {

    UserRepository userRepository;

    @Value
    public class RetrieveRequest {
        String username;
    }

    public Optional<User> execute(RetrieveRequest request) {
        return userRepository.findByUsername(request.getUsername());
    }
}
