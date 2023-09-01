package ba.sum.fsre.parking.Model;

import ba.sum.fsre.parking.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Molimo unesite Vašu email adresu.")
    @Email(message = "Unesite ispravnu email adresu.")
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @NotBlank(message="Molimo unesite Vašu lozinku.")
    @Column(nullable = false)
    private String password;
    @NotBlank(message="Molimo ponovite Vašu lozinku.")
    private String passwordRepeat;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean passwordsEqual;

    public void setPasswordsEqual(boolean passwordsEqual) {
        this.passwordsEqual = passwordsEqual;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }


    @Size(min=2, max=30, message="Vaše ime mora biti između 2 i 30 znakova duljine.")
    @Column(nullable = false, length = 30)
    private String firstName;


    @Size(min=2, max=30, message="Vaše prezime mora biti između 2 i 30 znakova duljine.")
    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = true, length = 30)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Spot> spots = new ArrayList<>();

    public User() {}

    public User(Long id, String email, String password, String passwordRepeat, Role role, boolean passwordsEqual, String firstName, String lastName, String phoneNumber, List<Spot> spots) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.role = role;
        this.passwordsEqual = passwordsEqual;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.spots = spots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    @AssertTrue(message="Lozinke se moraju podudarati.")
    public boolean isPasswordsEqual () {
        try {
            return this.password.equals(this.passwordRepeat);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
