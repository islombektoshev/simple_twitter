package uz.owl.service.mapper;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import uz.owl.domain.Authority;
import uz.owl.domain.Post;
import uz.owl.domain.User;
import uz.owl.repository.PostRepository;
import uz.owl.repository.UserRepository;
import uz.owl.security.SecurityUtils;
import uz.owl.service.dto.UserDTO;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    @Transactional
    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO(user);

        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        boolean isFollower = user.getFollowers().contains(sessionUser);
        boolean isFollowed = sessionUser.getFollowers().contains(user);
        userDTO.setFollowed(isFollowed);
        userDTO.setFollower(isFollower);

        int followerCount = user.getFollowers().size();
        userDTO.setFollowersCount((long) followerCount);

        int followedCount = userRepository.getFollowedUsers(user.getId()).size();
        userDTO.setFollowedCount((long) followedCount);

        List<Post> allByAuthorId = postRepository.findAllByAuthorId(user.getId());

        userDTO.setPostCount((long) allByAuthorId.size());

        return userDTO;
    }


    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
