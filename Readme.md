## MapStruct Mapping Facade

### Installation

Maven (pom.xml)
```
<dependencies>
    <dependency>
        <groupId>com.naharoo.commons</groupId>
        <artifactId>mapstruct-facade-spring-boot-starter</artifactId>
        <version>${mapstruct.facade.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>${java.version}</source>
                <target>${java.version}</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Gradle (build.gradle)
```
dependencies {
    annotationProcessor 'org.mapstruct:mapstruct-processor:${mapstruct.version}'
    implementation 'com.naharoo.commons:mapstruct-facade-spring-boot-starter:${mapstruct.facade.version}'
}
```
(Note: If you are also using Lombok, then it's annotation processor must be declared before MapStruct's annotation processor's declaration)

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
implementation('org.mapstruct:mapstruct') {
    version {
        strictly '${your.mapstruct.version}'
    }
}
```
(Note: If you are using Spring Boot BOM, or your application is extending Spring Boot Parent, then `org.springframework.boot:spring-boot-starter` dependency's version will be automatically adjusted).

### License
Mapstruct Mapping Facade is Open Source software released under the
[Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html)
