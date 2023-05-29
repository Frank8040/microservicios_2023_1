package com.example.auth.service.impl;

import com.example.auth.dto.AuthUserDto;
import com.example.auth.entity.Auth;
import com.example.auth.entity.TokenDto;
import com.example.auth.repository.AuthRepository;
import com.example.auth.security.JwtProvider;
import com.example.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    AuthRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public Auth save(AuthUserDto dto) {
        Optional<Auth> user = authUserRepository.findByName(dto.getName());
        if (user.isPresent())
            return null;
        String password = passwordEncoder.encode(dto.getPassword());
        Auth authUser = Auth.builder()
                .name(dto.getName())
                .password(password)
                .build();
        return authUserRepository.save(authUser);
    }
    @Override
    public TokenDto login(AuthUserDto dto) {
            Optional<Auth> user = authUserRepository.findByName(dto.getName());
            if(!user.isPresent())
                return null;
            if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
                return new TokenDto(jwtProvider.createToken(user.get()));
            return null;
        }

    @Override
    public TokenDto validate(String token) {
        if(!jwtProvider.validate(token))
            return null;
        String name = jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByName(name).isPresent())
            return null;
        return new TokenDto(token);
    }
}