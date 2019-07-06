package database.annotations.printer;

import database.managers.db_factory.TypeDatabase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks the package that it contains the necessary classes for print.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface PrinterPackage {
    TypeDatabase typeDB();
}
