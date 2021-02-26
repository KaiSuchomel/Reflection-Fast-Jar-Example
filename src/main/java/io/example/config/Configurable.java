package io.example.config;

import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.enterprise.context.Dependent;

@Unremovable
@RegisterForReflection
@Dependent
public interface Configurable {

    String name();

    String getDescription();

    String getDefaultValue();

    Class getType();

    default String getPropertyName() {
        return "Name." + name();
    }
}
