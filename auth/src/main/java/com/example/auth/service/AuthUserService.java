package com.example.auth.service;

import com.example.auth.dto.AuthUserDto;
import com.example.auth.dto.TokenDto;
import com.example.auth.entity.Auth;

public interface AuthUserService {
    public Auth save(AuthUserDto dto);
    public TokenDto login(AuthUserDto dto);

    public TokenDto validate(String token);

}