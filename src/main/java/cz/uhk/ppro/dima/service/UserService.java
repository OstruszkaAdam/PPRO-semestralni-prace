package cz.uhk.ppro.dima.service;

import cz.uhk.ppro.dima.model.Rating;
import cz.uhk.ppro.dima.model.User;
import cz.uhk.ppro.dima.model.UserDTO;
import cz.uhk.ppro.dima.repository.RatingRepository;
import cz.uhk.ppro.dima.repository.RoleRepository;
import cz.uhk.ppro.dima.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepo;
    private RoleRepository roleRepo;
    private RatingRepository ratingRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, RoleRepository roleRepo, RatingRepository ratingRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.ratingRepo = ratingRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createNewUser(@Valid UserDTO userDTO) throws DataAccessException {
        Optional<User> u = userRepo.findByUsername(userDTO.getUsername());
        if(u.isPresent()) return;

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFirstname(userDTO.getFirstname());
        user.setSurname(userDTO.getSurname());
        user.setRole(roleRepo.findByName("USER").get());
        user.setCreationTime(new Timestamp(System.currentTimeMillis()));
        userRepo.save(user);
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }

    @Transactional
    public void saveRating(Rating rating, User author, int ratedUserId) {
        rating.setAuthor(author);
        rating.setRatedUser(this.findById(ratedUserId).get());
        rating.setPostDate(new Timestamp(System.currentTimeMillis()));
        ratingRepo.save(rating);
    }

    @Transactional
    public Collection<User> findUsersByLastName(String lastName) throws DataAccessException {
        return userRepo.findByLastName(lastName);
    }

}
