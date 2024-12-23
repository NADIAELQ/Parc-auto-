package com.gl.parcauto.dto.response;


import com.gl.parcauto.dto.TokenType;

public record JwtAuthResponse (String token, TokenType type){
}
