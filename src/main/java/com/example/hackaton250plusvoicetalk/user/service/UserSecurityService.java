package com.example.hackaton250plusvoicetalk.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.user.persist.UserRepository;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService {

    private final UserRepository userRepository;

    public UserDetails loadUserByMobileNumber(String mobileNumber) throws UsernameNotFoundException {
        Optional<UserEntity> _userEntity = this.userRepository.findByMobileNumber(mobileNumber);
        if (_userEntity.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        UserEntity userEntity = _userEntity.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(userEntity.getAuthority().equals(Authority.ROLE_ADMIN)){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }

}