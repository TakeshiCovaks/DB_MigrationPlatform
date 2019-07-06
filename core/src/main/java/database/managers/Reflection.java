package database.managers;

import database.managers.db_factory.TypeDatabase;
import exception.ReflectionException;
import org.apache.log4j.Logger;
import strategy.Constants;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class for loading while running the necessary classes for work
 */
public class Reflection {
    private TypeDatabase typeDB = null;
    private String searchFile = "package-info.class";
    private static ResourceBundle bundle = ResourceBundle.getBundle(Constants.MESSAGES_FILE, Locale.US);
    private static final Logger LOG = Logger.getLogger(Reflection.class);

    public Reflection(TypeDatabase typeDB) {
        this.typeDB = typeDB;
    }

    /**
     * Method for reading classes at run time and calling for the formation
     * of the structure of the required instances of the class.
     *
     * @param packageAnnotation annotation for the package from which classes will be read.
     * @param classAnnotation   the annotation for the class to be loaded with in Map structure.
     */
    public <T> Map<String, T> readClassesRuntime(Class packageAnnotation, Class classAnnotation) {
        Map<String, T> mapInstance = new HashMap<>();
        try {
            LOG.debug(bundle.getString("startLoadClasses"));

            String dirWithInfo = searchDirWithFile(packageAnnotation);
            List<Class> listClasses = createListClasses(dirWithInfo, classAnnotation);
            if (listClasses == null) {
                mapInstance = loadClassesFromJar(packageAnnotation, classAnnotation);
            } else {
                mapInstance = createMapInstance(listClasses, classAnnotation);
            }


            LOG.debug(bundle.getString("loadClasses") + mapInstance.size());
        } catch (Exception e) {
            throw new ReflectionException(bundle.getString("cantReadRuntime"), e);
        }

        return mapInstance;
    }

    /**
     * Method for getting the root directory and invoking a recursive traversal to search.
     *
     * @param aClass required annotation type.
     * @return name found file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String searchDirWithFile(Class aClass) throws IOException, ClassNotFoundException, ReflectionException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(".");
        List<File> directories = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL res = resources.nextElement();
            directories.add(new File(res.getFile()));
        }

        for (File file: directories) {
            if (file.listFiles() == null) continue;
            String result = recursiveSearch(file, aClass);
            if (!result.isEmpty()) {
                return result;
            }
        }
        return "";
    }

    /**
     * Method for recursive directory traversal and search for the file "package-info.class".
     *
     * @param file   directory or file to search for a file.
     * @param aClass required annotation type.
     * @return name found file.
     * @throws ClassNotFoundException
     */
    private String recursiveSearch(File file, Class aClass) throws ClassNotFoundException, ReflectionException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile: files) {
                    String result = recursiveSearch(childFile, aClass);
                    if (!result.isEmpty()) {
                        return result;
                    }
                }
            }
        } else {
            if (file.getName().equals(searchFile)) {
                String pathInfoFile = file.getPath().replaceAll("^.+classes\\\\", "").replaceAll("\\\\", ".");
                Class clazz = Class.forName(pathInfoFile.replaceAll("\\.class", ""));
                try {
                    Annotation annotation = clazz.getAnnotation(aClass);
                    if (annotation != null) {
                        Method method = annotation.getClass().getDeclaredMethod("typeDB");
                        TypeDatabase valueTypeDB = (TypeDatabase) method.invoke(annotation, new Object[]{});
                        if (valueTypeDB.equals(typeDB)) {
                            return file.getParent();
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new ReflectionException(bundle.getString("cantFoundInfoDir"), e);
                }
            }
        }
        return "";
    }

    /**
     * Method for create a list of classes from the specified package.
     *
     * @param dirWithInfo     package path that contains the annotation we need.
     * @param classAnnotation class annotation, if the class contains this annotation we will add it to the list.
     * @return List of classes in the package that contains the necessary annotations.
     * @throws ClassNotFoundException
     */
    private static List<Class> createListClasses(String dirWithInfo, Class classAnnotation) throws ClassNotFoundException, ReflectionException {
        File dirInfo = new File(dirWithInfo);
        File[] files = dirInfo.listFiles();
        if (files == null) {
            return null;
        }
        List<Class> classes = new ArrayList<>();
        for (File file: files) {
            String pathInfoFile = file.getPath().replaceAll("^.+classes\\\\", "").replaceAll("\\\\", ".");
            Class clazz = Class.forName(pathInfoFile.replaceAll("\\.class", ""));
            if (clazz.getAnnotation(classAnnotation) != null) {
                classes.add(clazz);
            }
        }
        return classes;
    }

    /**
     * Method to create a Map structure with instance class from the list of classes.
     *
     * @param classes        input list of classes to process.
     * @param annotationType type of class annotation on which additional verification will be performed.
     * @return Map of instances created from the list of classes.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private <T> Map<String, T> createMapInstance(List<Class> classes, Class annotationType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Map<String, T> mapInstance = new HashMap<>();
        for (Class aClass: classes) {
            Annotation annotation = aClass.getAnnotation(annotationType);
            if (annotation != null) {
                addInstanceInMap(annotation, mapInstance, aClass);
            }
        }
        return mapInstance;
    }

    public <T> Map<String, T> loadClassesFromJar(Class aClass, Class classAnnotation) throws IOException, ReflectionException {

        String pathJarFileUrl = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String pathJarFile = pathJarFileUrl.replace("%20", " ");
        JarFile jarFile = new JarFile(pathJarFile);

        for (Enumeration<JarEntry> entries = jarFile.entries();
             entries.hasMoreElements(); ) {
            JarEntry entry = entries.nextElement();
            String file = entry.getName();
            if (file.endsWith(searchFile)) {
                String classname = file.replace('/', '.').substring(0, file.length() - 6);
                try {
                    Class<?> clazz = Class.forName(classname);
                    Annotation annotation = clazz.getAnnotation(aClass);
                    if (annotation != null) {
                        Method method = annotation.getClass().getDeclaredMethod("typeDB");
                        TypeDatabase valueTypeDB = (TypeDatabase) method.invoke(annotation, new Object[]{});
                        if (valueTypeDB.equals(typeDB)) {
                            return loadAllClassesFromJarDirectory(jarFile, file.replace(searchFile, ""), classAnnotation);
                        }
                    }
                } catch (Throwable e) {
                    throw new ReflectionException("Reflection operation failed. Map with instance classes not created", e);
                }
            }
        }
        return new HashMap<>();
    }

    /**
     * Method to bypass classes and add them in map.
     *
     * @param jarFile          jarFile with Loaders or Printers instances.
     * @param directoryForLoad directory in jarFile when located necessary instances.
     * @param classAnnotation  Load or Printer annotation.
     * @return filed map with instanced Loaders or Printers.
     */
    public <T> Map<String, T> loadAllClassesFromJarDirectory(JarFile jarFile, String directoryForLoad, Class classAnnotation) {
        Map<String, T> mapInstance = new HashMap<>();
        for (Enumeration<JarEntry> entries = jarFile.entries();
             entries.hasMoreElements(); ) {
            JarEntry entry = entries.nextElement();
            String file = entry.getName();
            if (file.startsWith(directoryForLoad) && file.endsWith(".class")) {
                String classname = file.replace('/', '.').substring(0, file.length() - 6);
                try {
                    Class<?> clazz = Class.forName(classname);
                    Annotation annotation = clazz.getAnnotation(classAnnotation);
                    if (annotation != null) {
                        addInstanceInMap(annotation, mapInstance, clazz);
                    }
                } catch (Throwable e) {
                    throw new ReflectionException("Reflection operation failen. Map with instance classes not created", e);
                }
            }
        }
        return mapInstance;
    }

    /**
     * Method for adding instance in matInstance
     *
     * @param annotation  for mark class Load or Print.
     * @param mapInstance map with instance Loaders or Printers.
     * @param clazz       Instances of the class Loader or Printer.
     */
    private <T> void addInstanceInMap(Annotation annotation, Map<String, T> mapInstance, Class clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Class<?> typeAnnotation = annotation.annotationType();
        Method m = typeAnnotation.getMethod("typeMeta");
        String typeMeta = (String) m.invoke(annotation);
        mapInstance.put(typeMeta, (T) clazz.newInstance());
    }
}
