package io.example.entity.base;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.envers.Audited;

/**
 * @author kai
 * @param <T>
 */
@Audited(withModifiedFlag = true)
@MappedSuperclass
public class BaseUUIDEntity<T extends BaseUUIDEntity> extends BaseEntity<T> {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @Schema(readOnly = true)
    private UUID id;

    @Column(name = "HAS_ATTRIBUTE", updatable = false)
    @Schema(readOnly=true, description = "True when the Entity has Attributes in the AttributeSystem", type = SchemaType.BOOLEAN, example = "FALSE")
    private Boolean hasAttribute = Boolean.FALSE;    
    
    @PrePersist
    protected void onCreateSetUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getter & setter">
    public T setId(UUID id) {
        this.id = id;
        return (T) this;
    }
    
    public UUID getId() {
        return id;
    }
    
    public Boolean isHasAttribute() {
        return hasAttribute;
    }
    
    public T setHasAttribute(Boolean hasAttribute) {
        this.hasAttribute = hasAttribute;
        return (T)this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="toString">
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", hasAttribute=" + hasAttribute + '}';
    }
    //</editor-fold>
}
