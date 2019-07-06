package database.annotations.load;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for class marking that at runtime information will be
 * taken into it and class methods will be executed depending on necessity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Load {
    String typeMeta();
}
