package io.strargazer.moneymanager.model.entity;

import io.strargazer.moneymanager.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "is_guest")
    private Boolean isGuest;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "total_asset", length = 38, scale = 2)
    private BigDecimal totalAsset;

    @Column(name = "date_reset_preference")
    private Integer dateResetPreference;


    public Boolean getIsGuest() {
        if (this.isGuest == null) {
            return false;
        }
        return this.isGuest;
    }

    public int getDateResetPreference() {
        return Objects.requireNonNullElse(this.dateResetPreference, 1);
    }

}
