package com.scm.v1.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.scm.v1.dto.UserDTO;
import com.scm.v1.entities.Providers;
import com.scm.v1.entities.User;
import com.scm.v1.exception.ScmException;
import com.scm.v1.repository.UserRepository;

import ch.qos.logback.core.spi.ScanException;


@Service
@CrossOrigin(origins = "http://localhost:3000")
public class UserService  {
    
    @Autowired 
    UserRepository userRepository;
    

    //  @Override
    // public UserDetails loadUserByUsername(String username) {
    //     User user = userRepository.findByUsername(username);
    //     if (user == null) return null;

    //     return new org.springframework.security.core.userdetails.User(
    //         user.getUsername(),
    //         user.getPassword(),
    //         List.of(new SimpleGrantedAuthority("ROLE_" + user.getRoles()))
    //     );
    // }


    public List<UserDTO> getUsers(){
        List<User> allUsers =  userRepository.findAll();
        List<UserDTO> users = StreamSupport.stream(allUsers.spliterator(), false)
                                   .map(user -> new UserDTO(user.getUserId(), user.getUsername(),
                                   user.getPhoneNumber(), user.getAddress(),
                                   user.getEmail(), null, user.getAbout(), user.getProfilePic(), user.getEnabled(), user.getEmailVerified(), user.getPhoneVerified(), user.getProviders(), null))
                                   .collect(Collectors.toList());


        return users;
    }

    public UserDTO getUser(String userId){

        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) throw new ScmException("first login in to see profile");
        
        return UserDTO.builder()
                .userId(user.get().getUserId())
                .username(user.get().getUsername())
                .phoneNumber(user.get().getPhoneNumber())
                .address(user.get().getAddress())
                .email(user.get().getEmail())
                .about(user.get().getAbout())
                .profilePic(user.get().getProfilePic())
                .enabled(user.get().getEnabled())
                .emailVerified(user.get().getEmailVerified())
                .phoneVerified(user.get().getPhoneVerified())
                .providers(user.get().getProviders())
                .build();
    }

    public UserDTO registerUser(UserDTO user) {

        User newUserPending = User.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .phoneNumber("")
        .about("")
        .address(user.getAddress())
        .enabled(false)
        .emailVerified(false)
        .phoneVerified(false)
        .providers(Providers.SELF)
        .build();

        User newUserCreated =  userRepository.save(newUserPending);
System.out.println("register user ---------"+newUserCreated);
        return UserDTO.builder()
                .userId(newUserCreated.getUserId())
                .username(newUserCreated.getUsername())
                .email(newUserCreated.getEmail())
                .about(newUserCreated.getAbout())
                .phoneNumber(newUserCreated.getPhoneNumber())
                .address(newUserCreated.getAddress())
                .profilePic(newUserCreated.getProfilePic())
                .enabled(newUserCreated.getEnabled())
                .emailVerified(newUserCreated.getEmailVerified())
                .phoneVerified(newUserCreated.getPhoneVerified())
                .providers(newUserCreated.getProviders())
                .build();
    }

    public Boolean deleteUser(String userId) {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public UserDTO updateUser(String userId, UserDTO userDTO) { // will return empty object if id not found
        
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            if (userDTO.getUsername() != null) {
                user.get().setUsername(userDTO.getUsername());
            }
            if (userDTO.getEmail() != null) {
                user.get().setEmail(userDTO.getEmail());
            }
            if (userDTO.getPassword() != null) {
                user.get().setPassword(userDTO.getPassword());
            }
            if (userDTO.getAbout() != null) {
                user.get().setAbout(userDTO.getAbout());
            }
            if (userDTO.getProfilePic() != null) {
                user.get().setProfilePic(userDTO.getProfilePic());
            }
            if (userDTO.getEnabled() != null) {
                user.get().setEnabled(userDTO.getEnabled());
            }
            if (userDTO.getPhoneVerified() != null) {
                user.get().setPhoneVerified(userDTO.getPhoneVerified());
            }
            if (userDTO.getEmailVerified() != null) {
                user.get().setEmailVerified(userDTO.getEmailVerified());
            }
            if (userDTO.getProviders() != null) {
                user.get().setProviders(userDTO.getProviders());
            }
            if(userDTO.getPhoneNumber()!=null){
                user.get().setPhoneNumber(userDTO.getPhoneNumber());
            }
            if(userDTO.getAddress()!=null){
                user.get().setAddress((userDTO.getAddress()));
            }

            User updatedUser = userRepository.save(user.get());    
            
            return UserDTO.builder()
            .userId(updatedUser.getUserId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .about(updatedUser.getAbout())
                .phoneNumber(updatedUser.getPhoneNumber())
                .address(updatedUser.getAddress())
                .profilePic(updatedUser.getProfilePic())
                .enabled(updatedUser.getEnabled())
                .emailVerified(updatedUser.getEmailVerified())
                .phoneVerified(updatedUser.getPhoneVerified())
                .providers(updatedUser.getProviders())
                .build();
        }
        return new UserDTO();
    }

    public UserDTO authenticateUser(UserDTO userDTO) {

        Optional<User> user =  userRepository.authenticateUser(userDTO.getUsername(), userDTO.getPassword());

        
        if(!user.isPresent()) throw new ScmException("invalid username or password");
        
        return UserDTO.builder()
        .userId(user.get().getUserId())
        .username(user.get().getUsername())
        .email(user.get().getEmail())
        .about(user.get().getAbout())
        .profilePic(user.get().getProfilePic())
        .enabled(user.get().getEnabled())
        .emailVerified(user.get().getEmailVerified())
        .phoneVerified(user.get().getPhoneVerified())
        .providers(user.get().getProviders())
        .build();
    }


}
