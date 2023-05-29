package com.example.auth.service;

import com.example.auth.dto.AuthUserDto;
import com.example.auth.entity.Auth;
import com.example.auth.entity.TokenDto;

public interface AuthUserService {
    public Auth save(AuthUserDto dto);
    public TokenDto login(AuthUserDto dto);
    public TokenDto validate(String token);
}