package io.example.startup;

import io.example.config.Configurable;
import io.example.entity.base.BaseUUIDEntity;
import io.quarkus.runtime.StartupEvent;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;

@ApplicationScoped
public class ServiceStartup {

    @Inject
    Instance<Configurable> configurableInstances;
    
    @Inject
    Instance<BaseUUIDEntity<?>> baseuuidInstances;
    
    public void onStart(@Observes StartupEvent ev) {
        System.out.println("Application is starting!!");
        printOutput();
    }

    protected void printOutput() {
        System.out.println("--Start-GetConfigurables via Class--");
        getConfigurables(false).forEach(x -> System.out.println("Configurable:" + x));
        System.out.println("--Finish-GetConfigurables via Class--");
        //
        System.out.println("--Start-GetConfigurables via Packagename--");
        getConfigurables(true).forEach(x -> System.out.println("Configurable:" + x));
        System.out.println("--Finish-GetConfigurables via Packagename--");
        //
        System.out.println("--Start-GetConfigurables via Instances--");
        configurableInstances.stream().forEach(x -> System.out.println("Configurable:" + x.getClass().getSimpleName()));
        System.out.println("--Finish-GetConfigurables via Instances--");
        //
        System.out.println("--Start-GetEntities via Class--");
        getEntities(false).forEach(x -> System.out.println("Entity:" + x));
        System.out.println("--Finish-GetEntities via Class--");
        //
        System.out.println("--Start-GetEntities via Packagename--");
        getEntities(true).forEach(x -> System.out.println("Entity:" + x));
        System.out.println("--Finish-GetEntities via Packagename--");
        //
        System.out.println("--Start-GetEntities via Instances--");
        baseuuidInstances.stream().forEach(x -> System.out.println("Entity:" + x.getClass().getSimpleName()));
        System.out.println("--Finish-GetEntities via Instances--");
    }

//    @Scheduled(every = "5s")
//    public void printConfigurables() {
//        printOutput();
//    }

    public Stream<Class<? extends BaseUUIDEntity>> getEntities(boolean viaPackageName) {
        try {
            if (viaPackageName) {
                return new Reflections("io.example")
                        .getSubTypesOf(BaseUUIDEntity.class).stream();
            }
            return new Reflections(BaseUUIDEntity.class)
                    .getSubTypesOf(BaseUUIDEntity.class).stream();
        } catch (Exception ex) {
            System.out.println("ServiceStartup.getEntities viaPackageName:" + viaPackageName + "Exception:" +  ex.getMessage());
            return Stream.empty();
        }
    }

    Stream<Configurable> getConfigurables(boolean viaPackageName) {
        if (viaPackageName) {
            try {
                return new Reflections("io.example")
                        .getSubTypesOf(Configurable.class).stream()
                        .map(c -> {
                            Configurable[] configurables = null;
                            if (c.isEnum()) {
                                configurables = c.getEnumConstants();
                            }
                            return configurables;
                        })
                        .flatMap(Arrays::stream);
            } catch (ReflectionsException rex) {
                System.out.println("ServiceStartup.getConfigurables() - No Configurables defined !!!" + rex.getMessage());
                return Stream.empty();
            }
        } else {
            try {
                return new Reflections(Configurable.class)
                        .getSubTypesOf(Configurable.class).stream()
                        .map(c -> {
                            Configurable[] configurables = null;
                            if (c.isEnum()) {
                                configurables = c.getEnumConstants();
                            }
                            return configurables;
                        })
                        .flatMap(Arrays::stream);
            } catch (ReflectionsException rex) {
                System.out.println("ServiceStartup.getConfigurables() - No Configurables defined !!!" + rex.getMessage());
                return Stream.empty();
            }
        }
    }
}
