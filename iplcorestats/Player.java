package com.careerit.jfs.iplcorestats;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String name;
    private String role;
    private double amount;
    private String country;
    private String team;

}

