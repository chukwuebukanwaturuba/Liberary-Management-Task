package com.ebuka.librarymanagementsystem.model.entities;

import com.ebuka.librarymanagementsystem.model.enums.Role;
import com.ebuka.librarymanagementsystem.utils.AuditorClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Patron")
@Entity
public class Patron  implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email_address")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "House_address")
    private String address;
    @Column(name = "phone_number ")
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BorrowBook> borrowedBooks = new ArrayList<>();


    public Patron(long l, String johnDoe, String mail, String password123, String s, String number, Role role) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
