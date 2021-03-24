package com.socialgame.alpha.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String gameType;


    @OneToMany
    private Set<Player> players;


}
