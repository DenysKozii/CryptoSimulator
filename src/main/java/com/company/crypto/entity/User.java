package com.company.crypto.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @NonNull
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 50,
            message = "Length of first name should be between 2 and 50")
    private String username;

    @NonNull
    @NotBlank(message = "Must not be empty")
    @Size(min = 3, message = "Password is not strong enough")
    @Column(name = "password")
    private String password;

    @Column
    private Double usdt;

    private Double pnl;

    @Column(name = "question_order_id")
    private Long questionOrderId;

    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Asset> assets = new ArrayList<>();

    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    @NonNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return username;
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
