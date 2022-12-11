package io.strargazer.moneymanager.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@NoArgsConstructor
@Data
public class BaseEntity implements Serializable {
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(
            name = "updated_at",
            nullable = false
    )
    @UpdateTimestamp
    private Timestamp updatedAt;

    private String createdBy;
    private String updatedBy;
}
