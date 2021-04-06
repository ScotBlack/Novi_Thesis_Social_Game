package com.socialgame.alpha.domain;

import javax.persistence.*;

@Entity
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String gameType;
    @Column
    private int points;

    @Column
    private String status;
    @Column
    private Boolean canStart;



}
