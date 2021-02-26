package io.example.config;

import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.enterprise.context.Dependent;

@Dependent
@Unremovable
@RegisterForReflection
public enum ServiceConfiguration implements Configurable {

    @Dependent
    @Unremovable
    SERVICECONFIG1("CONFIG1", "DefaultConfig1", String.class),
    SERVICECONFIG2("CONFIG2", "DefaultConfig2", String.class),
    SERVICECONFIG3("CONFIG3", "DefaultConfig3", String.class);

    private final String description;
    private final String defaultValue;
    private final Class type;

    private ServiceConfiguration(String aDescription, String aDefaultValue, Class aType) {
        description = aDescription;
        defaultValue = aDefaultValue;
        type = aType;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Class getType() {
        return type;
    }

    @Override
    public String toString() {
        return getPropertyName();
    }
}
