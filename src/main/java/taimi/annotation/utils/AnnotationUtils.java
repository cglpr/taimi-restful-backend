package taimi.annotation.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.data.mongodb.core.mapping.Document;

public class AnnotationUtils {
	public static String getCollectionName(Class<?> klazz) throws Exception {
		Annotation annotation = klazz.getAnnotation(Document.class);
	    Class<? extends Annotation> type = annotation.annotationType();
	   
	    Method method = type.getDeclaredMethod("collection");
	    String collectionName = (String) method.invoke(annotation, (Object[])null);

	    return collectionName;
	}
}
