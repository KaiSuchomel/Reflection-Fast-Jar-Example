package io.example.entity.base;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.envers.Audited;

@Audited(withModifiedFlag = true)
@MappedSuperclass
public class BaseEntity<T extends BaseEntity> implements Serializable {

    @Column(name = "CREATED", nullable = false, updatable = false)
    @Schema(readOnly = true, implementation = String.class, format = "dateTime", example = "02.01.2019 01:30:56,123")
    private OffsetDateTime created;

    @Column(name = "STAMP", nullable = false)
    @Schema(readOnly = true, implementation = String.class, format = "dateTime", example = "02.01.2019 01:30:56,001")
    private OffsetDateTime stamp;

    @Version
    @Column(name = "VERSION")
    @Schema(readOnly = true, example = "1")
    private Long version;

    //<editor-fold defaultstate="collapsed" desc="getter & setter">
    public Long getVersion() {
        return version;
    }
    
    public T setVersion(Long version) {
        this.version = version;
        return (T) this;
    }
    
    public OffsetDateTime getCreated() {
        return created;
    }
    
    public T setCreated(OffsetDateTime created) {
        this.created = created;
        return (T) this;
    }
    
    public OffsetDateTime getStamp() {
        return stamp;
    }
    
    public T setStamp(OffsetDateTime stamp) {
        this.stamp = stamp;
        return (T) this;
    }
    //</editor-fold>

    @PrePersist
    protected void onCreate() {
        created = stamp = OffsetDateTime.now();
        version = 1l;
    }

    @PreUpdate
    protected void onUpdate() {
        stamp = OffsetDateTime.now();
    }
}
