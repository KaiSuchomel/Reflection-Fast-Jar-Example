# Reflection-Fast-Jar-Example project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

## Structure of the Project

- We have a enum `ServiceConfiguration` that implements the Interface `Configurable`.
- We have 2 Entities `Entity1` and `Entity2` that extend `BaseUUIDEntity`.

The aim is to lookup all Instances of `Configurable` and `BaseUUIDEntity`.

In the Class `ServiceStartup` several lookups are performed.

1.  Try to get all Configurables via `org.reflection.Reflection` and Class
2.  Try to get all Configurables via `org.reflection.Reflection` and Packagename `io.example`
3.  Try to get all Configurables via `javax.enterprise.inject.Instance`
4.  Try to get all BaseUUIDEntities via `org.reflection.Reflection` and Class
5.  Try to get all BaseUUIDEntities via `org.reflection.Reflection` and Packagename `io.example`
6.  Try to get all BaseUUIDEntities via `javax.enterprise.inject.Instance`

We get a different behaviour when the Jar is packaged in `legay-jar` Mode or `fast-jar` Mode.

More details see below:

## Legacy Jar

Package the Project as a Legacy Jar

```shell script
mvn clean package -Dquarkus.package.type=legacy-jar
```

Execute the jar

```shell script
java -jar target/Reflection-Fast-Jar-Example-1.0.0-SNAPSHOT-runner.jar
```

Output:

```shell
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/
2021-02-26 11:39:48,392 INFO  [io.qua.sch.run.SimpleScheduler] (main) No scheduled business methods found - Simple scheduler will not be started
Application is starting!!
--Start-GetConfigurables via Class--
2021-02-26 11:39:48,423 INFO  [org.ref.Reflections] (main) Reflections took 22 ms to scan 1 urls, producing 5 keys and 3 values
Configurable:Name.SERVICECONFIG1
Configurable:Name.SERVICECONFIG2
Configurable:Name.SERVICECONFIG3
--Finish-GetConfigurables via Class--
--Start-GetConfigurables via Package--
2021-02-26 11:39:48,439 INFO  [org.ref.Reflections] (main) Reflections took 10 ms to scan 1 urls, producing 27 keys and 21 values
Configurable:Name.SERVICECONFIG1
Configurable:Name.SERVICECONFIG2
Configurable:Name.SERVICECONFIG3
--Finish-GetConfigurables via Package--
--Start-GetConfigurables via Instances--
--Finish-GetConfigurables via Instances--
--Start-GetEntities via Class--
2021-02-26 11:39:48,443 INFO  [org.ref.Reflections] (main) Reflections took 2 ms to scan 1 urls, producing 8 keys and 6 values
Entity:class io.example.entity.base.BaseUUIDEntity$HibernateProxy$1yJUWXYo
--Finish-GetEntities via Class--
--Start-GetEntities via Package--
2021-02-26 11:39:48,452 INFO  [org.ref.Reflections] (main) Reflections took 5 ms to scan 1 urls, producing 27 keys and 21 values
Entity:class io.example.entity.Entity1$HibernateProxy$EzJ8tJgJ
Entity:class io.example.entity.Entity2
Entity:class io.example.entity.Entity1
Entity:class io.example.entity.base.BaseUUIDEntity$HibernateProxy$1yJUWXYo
Entity:class io.example.entity.Entity2$HibernateProxy$6JARE3KH
--Finish-GetEntities via Package--
--Start-GetEntities via Instances--
Entity:Entity2
Entity:Entity1
--Finish-GetEntities via Instances--
2021-02-26 11:39:48,543 INFO  [io.quarkus] (main) Reflection-Fast-Jar-Example 1.0.0-SNAPSHOT on JVM (powered by Quarkus 1.12.0.Final) started in 2.056s. Listening on: http://0.0.0.0:8080
2021-02-26 11:39:48,544 INFO  [io.quarkus] (main) Profile prod activated.
2021-02-26 11:39:48,545 INFO  [io.quarkus] (main) Installed features: [agroal, cdi, hibernate-envers, hibernate-orm, jdbc-postgresql, mutiny, narayana-jta, scheduler, smallrye-context-propagation, smallrye-openapi]
```

### Conclussion

- Configurables can be loaded:
  - [x] via Reflection Class
  - [x] via Reflection Package
  - [ ] via Instance
- BaseUUIDEntities can be loaded:
  - [ ] via Reflection Class
  - [x] via Reflection Package
  - [x] via Instance

## Fast Jar

Package the Project as a Legacy Jar

```shell script
mvn clean package
```

Execute the jar

```shell script
java -jar target/quarkus-app/quarkus-run.jar
```

Output:

```shell
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/
2021-02-26 12:03:11,510 INFO  [io.qua.sch.run.SimpleScheduler] (main) No scheduled business methods found - Simple scheduler will not be started
Application is starting!!
--Start-GetConfigurables via Class--
2021-02-26 12:03:11,532 INFO  [org.ref.Reflections] (main) Reflections took 15 ms to scan 1 urls, producing 5 keys and 3 values
Configurable:Name.SERVICECONFIG1
Configurable:Name.SERVICECONFIG2
Configurable:Name.SERVICECONFIG3
--Finish-GetConfigurables via Class--
--Start-GetConfigurables via Package--
2021-02-26 12:03:11,536 WARN  [org.ref.Reflections] (main) given scan urls are empty. set urls in the configuration
ServiceStartup.getConfigurables() - No Configurables defined !!!Scanner SubTypesScanner was not configured
--Finish-GetConfigurables via Package--
--Start-GetConfigurables via Instances--
--Finish-GetConfigurables via Instances--
--Start-GetEntities via Class--
2021-02-26 12:03:11,538 INFO  [org.ref.Reflections] (main) Reflections took 1 ms to scan 1 urls, producing 5 keys and 4 values
--Finish-GetEntities via Class--
--Start-GetEntities via Package--
2021-02-26 12:03:11,539 WARN  [org.ref.Reflections] (main) given scan urls are empty. set urls in the configuration
ServiceStartup.getEntities viaPackage:trueException:Scanner SubTypesScanner was not configured
--Finish-GetEntities via Package--
--Start-GetEntities via Instances--
Entity:Entity2
Entity:Entity1
--Finish-GetEntities via Instances--
2021-02-26 12:03:11,615 INFO  [io.quarkus] (main) Reflection-Fast-Jar-Example 1.0.0-SNAPSHOT on JVM (powered by Quarkus 1.12.0.Final) started in 1.647s. Listening on: http://0.0.0.0:8080
2021-02-26 12:03:11,616 INFO  [io.quarkus] (main) Profile prod activated.
2021-02-26 12:03:11,616 INFO  [io.quarkus] (main) Installed features: [agroal, cdi, hibernate-envers, hibernate-orm, jdbc-postgresql, mutiny, narayana-jta, scheduler, smallrye-context-propagation, smallrye-openapi]

```

### Conclussion

- Configurables can be loaded:
  - [x] via Reflection Class
  - [ ] via Reflection Package
  - [ ] via Instance
- BaseUUIDEntities can be loaded:
  - [ ] via Reflection Class
  - [ ] via Reflection Package
  - [x] via Instance

## Questions

- Why are Configurables not able to be loaded via Instance??
- Why is in FastJar the load of Configurables with Packagename not possible? `given scan urls are empty. set urls in the configuration`
- In Fast-Jar Mode the BaseUUIDEntities are not loadbale via Packagename. Why??
