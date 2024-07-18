package com.paintingscollectors.service;

import com.paintingscollectors.model.dto.UserLoginDto;
import com.paintingscollectors.model.dto.UserPaintingsInfoDTO;
import com.paintingscollectors.model.dto.UserRegisterDto;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final CurrentUser currentUser;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    public boolean saveUser(UserRegisterDto userRegisterDto) {

        boolean isUsernameOrEmailTaken = userRepository.existsByUsernameOrEmail(userRegisterDto.getEmail(),userRegisterDto.getUsername());
        if (isUsernameOrEmailTaken){
            return false;
        }
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            return false;
        }
        User map = modelMapper.map(userRegisterDto, User.class);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        userRepository.save(map);
        return true;
    }

    public boolean loginUser(UserLoginDto userLoginDto) {

        Optional<User> optional = userRepository.findByUsername(userLoginDto.getUsername());

        if (optional.isEmpty()){
            return false;
        }
        User user = optional.get();
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            return false;
        }
        currentUser.setUser(user);
        return true;
    }
    public void logout() {
        currentUser.logout();
    }

    @Transactional
    public List<UserPaintingsInfoDTO> getUserPaintings(long id) {
        List<UserPaintingsInfoDTO> collect = userRepository.getById(id)
                .getPaintings()
                .stream()
                .map(p -> modelMapper.map(p, UserPaintingsInfoDTO.class)).collect(Collectors.toList());
        return collect;
    }

    @Transactional
    public List<UserPaintingsInfoDTO> getUserFavoritePaintings(long id) {
        return userRepository.getById(id)
                .getFavoritePaintings()
                .stream()
                .map(painting -> modelMapper.map(painting,UserPaintingsInfoDTO.class))
                .collect(Collectors.toList());
    }

}
