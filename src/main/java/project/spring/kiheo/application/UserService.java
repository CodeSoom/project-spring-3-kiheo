package project.spring.kiheo.application;

import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.spring.kiheo.domain.User;
import project.spring.kiheo.domain.UserRepository;
import project.spring.kiheo.dto.UserRegistrationData;
import project.spring.kiheo.errors.UserEmailDuplicationException;
import project.spring.kiheo.errors.UserNotFoundException;

@Slf4j
@Service
/*@Transactional*/
public class UserService {
    private final Mapper mapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(Mapper dozerMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.mapper = dozerMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUser(String email) {
        return userRepository.findByEmailAndDeletedIsFalse(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public User registerUser(UserRegistrationData registrationData) {
        log.info("==="+registrationData.getPassword());
        log.info("==="+registrationData.getEmail());
        String email = registrationData.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserEmailDuplicationException(email);
        }
        User user = mapper.map(registrationData, User.class);
        user.changePassword(registrationData.getPassword(), passwordEncoder);
        return userRepository.save(user);
    }

    /*public User updateUser(Long id, UserModificationData modificationData, Long userId)
    throws AccessDeniedException {
        if (id != userId){
            throw new AccessDeniedException("Access denied");
        }
        User user = findUser(id);

        User source = mapper.map(modificationData, User.class);
        user.changeWith(source);

        return user;
    }

    public User deleteUser(Long id) {
        User user = findUser(id);
        user.destroy();
        return user;
    }*/


}