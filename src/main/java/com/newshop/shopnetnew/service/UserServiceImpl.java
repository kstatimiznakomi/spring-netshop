package com.newshop.shopnetnew.service;


import com.newshop.shopnetnew.dao.OrderRepository;
import com.newshop.shopnetnew.dao.UserRepository;
import com.newshop.shopnetnew.domain.Bucket;
import com.newshop.shopnetnew.domain.Order;
import com.newshop.shopnetnew.domain.Role;
import com.newshop.shopnetnew.domain.User;
import com.newshop.shopnetnew.dto.UserDTO;
import com.newshop.shopnetnew.mapper.UserMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BucketService bucketService;
    private final UserService userService;
    private final UserMapper userMapper = UserMapper.MAPPER;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private User user;
    private final SimpMessagingTemplate template;

    public UserServiceImpl(@Lazy UserRepository userRepository, @Lazy BucketService bucketService, @Lazy UserService userService, PasswordEncoder passwordEncoder, OrderRepository orderRepository, SimpMessagingTemplate template){
        super();
        this.userRepository = userRepository;
        this.bucketService = bucketService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
        this.template = template;
    }

    @Override
    @Transactional
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())){
            throw new RuntimeException("Неправильный пароль");
        }
        User user = userService.findByName(userDTO.getUsername());
        if (user == null) {
            User newUser = User.builder()
                    .username(userDTO.getUsername())
                    .personName(userDTO.getPersonName())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .email(userDTO.getEmail())
                    .role(Role.CLIENT)
                    .bucket(Bucket.builder().build())
                    .build();
            userRepository.save(newUser);
            Bucket bucket = bucketService.getBucketByUser(newUser);
            newUser.setBucket(bucket);
            userService.save(newUser);
        }
        return true;
    }
    @Override
    public boolean exist(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())){
            throw new RuntimeException("Неправильный пароль");
        }

        User user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveFromAdmin(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())){
            throw new RuntimeException("Неправильный пароль");
        }
        User user = User.builder()
                .username(userDTO.getUsername())
                .personName(userDTO.getPersonName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .bucket(new Bucket())
                .build();
        userRepository.save(user);
        return true;
    }



    @Override
    public void save(User user) {
        userRepository.save(user);
    }



    @Transactional
    public void addUser(UserDTO dto) {
        User user  = userMapper.toUser(dto);
        User savedUser = userRepository.save(user);
        template.convertAndSend("/topic/users",
                UserMapper.MAPPER.fromUser(savedUser));
    }

    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .personName(user.getPersonName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByUsername(name);
    }


    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User savedUser = userRepository.findFirstByUsername(dto.getUsername());
        if(savedUser == null) throw new RuntimeException("User not found by name: " + dto.getUsername());
        boolean isChanged = false;
        if(dto.getPassword() != null && !dto.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }
        if(!Objects.equals(dto.getEmail(), savedUser.getEmail())){
            savedUser.setEmail(dto.getEmail());
            isChanged = true;
        }
        if(isChanged) userRepository.save(savedUser);
    }

    @Override
    public String signUpUser(User user){
        return "";
    }
}
