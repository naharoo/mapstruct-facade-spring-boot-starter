### External Dependencies
This library has only two dependencies:
1. `org.springframework.boot:spring-boot-starter`
2. `org.mapstruct:mapstruct`.
They will be promoted transitively. 
So their versions may be different from your application's existing dependencies'.
In such cases, to avoid dependency conflicts you can adjust their versions in your local `dependencyManagement` configurations like so:

Maven
```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${your.mapstruct.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Gradle
```

```
(Note: If you are using Spring Boot BOM, or your application is extending Spring Boot Parent, then `org.springframework.boot:spring-boot-starter` dependency's version will be automatically adjusted).