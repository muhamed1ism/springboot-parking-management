package ba.sum.fsre.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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

    public User() {}

    public User(Long id, String email, String password, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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

    @AssertTrue(message="Lozinke se moraju podudarati.")
    public boolean isPasswordsEqual () {
        try {
            return this.password.equals(this.passwordRepeat);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
