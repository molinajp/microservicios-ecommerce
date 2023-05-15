package com.undefined.vault.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String mysqlUser;
    private String mysqlPass;
    private String mongoUser;
    private String mongoPass;
}
