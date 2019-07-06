package database.annotations.load;

import database.managers.db_factory.TypeDatabase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks the package that it contains the necessary
 * classes for loading metadata from the database
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface LoadPackage {
    TypeDatabase typeDB();
}
