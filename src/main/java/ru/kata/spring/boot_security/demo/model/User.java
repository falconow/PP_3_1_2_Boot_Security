package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "login", columnList = "login", unique = true),
                @Index(name = "email", columnList = "email", unique = true)
        }
)

public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "login")
   private String login;

   @Column(name = "password")
   private String password;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "email")
   private String email;

   @Column(name = "phone")
   private String phone;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(name = "users_role",
           joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
   )
   private List<Role> roles = new ArrayList<>();


   public User() {}

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
   }

   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return login;
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

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Role> getCollectionsRoles() {
      return roles;
   }


   public void setCollectionsRoles(List<Role> collectionsRoles) {
      roles = collectionsRoles;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", login='" + login + '\'' +
              ", password='" + password + '\'' +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", phone='" + phone + '\'' +
              '}';
   }

}
