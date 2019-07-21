package com.mashup.molinkfirst.user;

import com.mashup.molinkfirst.exception.AlreadyExistsException;
import com.mashup.molinkfirst.exception.NotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(String phoneUuid){
    if(userRepository.findByPhoneUuid(phoneUuid).isPresent()){
      throw new AlreadyExistsException("이미 등록된 유저입니다.");
    }

    User user = User.builder()
        .phoneUuid(phoneUuid)
        .build();

    userRepository.save(user);
    return user;
  }

  public User findUser(String phoneUuid){
    Optional<User> user = userRepository.findByPhoneUuid(phoneUuid);
    if (!user.isPresent()) throw new NotFoundException("Cannot find User!");

    return userRepository.findById(user.get().getId()).orElseThrow(() -> new NotFoundException("Cannot find User!"));
  }
}
