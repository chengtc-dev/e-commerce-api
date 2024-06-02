package dev.chengtc.ecommerceapi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@Entity @Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "email", nullable = false, length = 64, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
