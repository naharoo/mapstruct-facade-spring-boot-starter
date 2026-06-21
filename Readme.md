## MapStruct Mapping Facade

### Overview
This is a generic Facade over MapStruct mapping Library.
MapStruct gives as Java POJOs compile-time mapping, we give you a single entry and use point to all registered MapStruct mappers in Spring Boot environment. We use some spring-based reflection here to collect mapping metadata and register mappings.

### Requirements
| Library version | Java   | Spring Boot |
|-----------------|--------|-------------|
| `2.x`           | 17+    | 4.x         |
| `1.x`           | 8+     | 2.x         |

Version `2.0.0` is a breaking upgrade: it moves the baseline to **Java 17** and **Spring Boot 4.x** (Jakarta namespace). Stay on the `1.x` line for Java 8 / Spring Boot 2.x.

### How it works?
Let's say you have 2 Java POJOs, `Car` and `CarDto`.
```
public class Car {
    private final String name;
    private final int year;

    public Car(String name, int year) { // implementation }
}

public class CarDto {
    private final String name;
    private final int year;

    public CarDto(String name, int year) { // implementation }
}
```

You have an instance of `Car` and need to convert it into `CarDto`. In the first place, let's declare a mapper in MapStruct style and extend it from UnidirectionalMapper interface. It will give us unidirectional mapping abstractions. Don't forget to annotate it with MapStruct's `@Mapper` annotation and set `componentModel` to `"spring"`.
```
@Mapper(componentModel = "spring")
public interface CarMapper extends UnidirectionalMapper<Car, CarDto> {}
```

That's it. We've configured and registered a mapping! Now we can inject `MappingFacade` and map our POJOs.
```
@Component
public class OurBusinessBean {
    
    @Autowired
    private MappingFacade mappingFacade;
    
    public CarDto doMap(Car car) {
        return mappingFacade.map(car, CarDto.class);
    }
}
```

We also support polymorphic type mappings, e.g. mapping two hierarchies. You just need to register all concrete mappings and we will handle everything else.
```
public abstract class Message {}
public class TextMessage extends Message {}
public class ImageMessage extends Message {}
public class VideoMessage extends Message {}
```
```
public abstract class MessageDto {}
public class TextMessageDto extends MessageDto {}
public class ImageMessageDto extends MessageDto {}
public class VideoMessageDto extends MessageDto {}
```
```
@Mapper(componentModel = "spring")
public interface TextMessageMapper extends UnidirectionalMapper<TextMessage, TextMessageDto> {}

@Mapper(componentModel = "spring")
public interface ImageMessageMapper extends UnidirectionalMapper<ImageMessage, ImageMessageDto> {}

@Mapper(componentModel = "spring")
public interface VideoMessageMapper extends UnidirectionalMapper<VideoMessage, VideoMessageDto> {}
```
```
TextMessageDto textMessageDto = mappingFacade.map(new TextMessage(), TextMessageDto.class);
MessageDto imageMessageDto = mappingFacade.map(new ImageMessage(), MessageDto.class);
MessageDto videoMessageDto = mappingFacade.map(new VideoMessage(), MessageDto.class);
```

If MapStruct's default generated mappings are not enough, you can always write default implementation for UnidirectionalMapper's `map` method when registering a mapping.
Or even use MapStruct's decorators for some specific cases.


### Installation

Maven (pom.xml)
```
<dependencies>
    <dependency>
        <groupId>com.naharoo.commons</groupId>
        <artifactId>mapstruct-facade-spring-boot-starter</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <release>17</release>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>1.6.3</version>
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
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.6.3'
    implementation 'com.naharoo.commons:mapstruct-facade-spring-boot-starter:2.0.0'
}
```
(Note: If you are using Lombok, then you should also include `org.projectlombok:lombok-mapstruct-binding`)

### External Dependencies
This library has only the following compile dependencies:
1. `org.springframework.boot:spring-boot-starter`
2. `org.mapstruct:mapstruct`
3. `org.jspecify:jspecify` (nullability annotations)

They will be promoted transitively. Proxy detection for `org.hibernate.orm:hibernate-core` and `org.javassist:javassist` is supported but those dependencies are `optional` — they are only used when your application already provides them on the classpath.
So their versions may be different from your application's existing dependencies'.
In such cases, to avoid dependency conflicts you can adjust their versions in your local `dependencyManagement` configurations like so:

Maven
```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>1.6.3</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Gradle
```
dependencyManagement {
    dependencies {
        dependency("org.mapstruct:mapstruct:1.6.3")
    }
}
```
(Note: If you are using Spring Boot BOM, or your application is extending Spring Boot Parent, then `org.springframework.boot:spring-boot-starter` dependency's version will be automatically adjusted).

### License
Mapstruct Mapping Facade is Open Source software released under the
[Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html)
