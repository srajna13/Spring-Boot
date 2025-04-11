package com.example.WalletApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;
}
