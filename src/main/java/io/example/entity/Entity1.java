package io.example.entity;

import io.example.entity.base.BaseUUIDEntity;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.envers.Audited;

@Dependent
@Entity
@Table(name = "Entity1", schema = "TEST", uniqueConstraints = { @UniqueConstraint(columnNames = {"NAME"}) })
@Schema(name = "Entity1", description = "Entity1")
@Audited(withModifiedFlag = true)
public class Entity1 extends BaseUUIDEntity<Entity1> implements Serializable {
    
    @Schema(hidden = true, readOnly = true)
    private static final long serialVersionUID = -1L;

    @Basic(optional = false)
    @Column(name = "NAME", updatable = false, unique = true, nullable = false)
    @Schema(example = "Name000")
    private String name;
    
    @Basic(optional = true)
    @Column(name = "DESCRIPTION")
    @Schema(example = "Desc000")
    private String description;

    public String getName() {
        return name;
    }

    public Entity1 setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Entity1 setDescription(String description) {
        this.description = description;
        return this;
    }
}
