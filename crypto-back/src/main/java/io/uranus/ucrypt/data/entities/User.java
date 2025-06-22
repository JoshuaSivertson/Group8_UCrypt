package io.uranus.ucrypt.data.entities;

import io.uranus.ucrypt.data.constants.AppConstants;
import io.uranus.ucrypt.data.entities.enums.UserStatus;
import io.uranus.ucrypt.data.entities.shared.AuditedEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class User extends AuditedEntity {

    @NotBlank
    @Length(min = AppConstants.MIN_NAME_LENGTH, max = AppConstants.MAX_NAME_LENGTH)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email(regexp = AppConstants.EMAIL_REGEX, message = AppConstants.EMAIL_REGEX_NOT_VALID_ERROR_MESSAGE)
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Pattern(regexp = AppConstants.PASSWORD_REGEX, message = AppConstants.PASSWORD_REGEX_NOT_VALID_ERROR_MESSAGE)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.CREATED;

    @NotNull
    @ManyToOne
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<EncryptionKey> encryptionKeys;

    public User(String name, String email, String password, UserStatus status, Role role, List<EncryptionKey> encryptionKeys) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.encryptionKeys = encryptionKeys;
    }
}
