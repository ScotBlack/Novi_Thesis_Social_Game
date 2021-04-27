package com.socialgame.alpha.domain;

import com.socialgame.alpha.domain.enums.ERole;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Role {


//    public static final String USER_HOST = "USER_HOST";
//    public static final String AUTHOR_ADMIN = "AUTHOR_ADMIN";
//    public static final String BOOK_ADMIN = "BOOK_ADMIN";
//
//    private String authority;


    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(columnDefinition = "serial")
    private long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}