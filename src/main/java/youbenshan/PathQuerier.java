package youbenshan;

import java.time.Instant;
import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class PathQuerier {
	public static <T> Specification<T> query(Condition... conditions) {
		return (root, query, cb) -> Arrays.stream(conditions).map(c -> predicate(cb, root, c)).reduce(cb::and)
				.orElseGet(cb::conjunction);
	}
	
	private static <T extends Comparable<? super T>, E extends Enum<E>> Predicate predicate(CriteriaBuilder builder, final Root<?> root,
			Condition condition) {
		
		Path<T> path = getPath(root, condition.getNamePath());
		return condition.getOperator().predicate(path, getValue(path.getJavaType(),condition.getValue() ), builder);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<? super T>, E extends Enum<E>> T getValue(Class<T> javaType, String value){
		Object result =null;
		if (javaType.isEnum()){
			Class<E> enumClass=(Class<E>)javaType;
			result= Enum.valueOf(enumClass, value);
		}else if(javaType.equals(boolean.class) || javaType.equals(Boolean.class) ){
			result = Boolean.valueOf(value);
		}else if(javaType.equals(Instant.class)){
			result = Instant.parse(value);
		}else{
			result=value;
		}
		return (T)result;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<? super T>> Path<T> getPath(final Root<?> root, String namePathString) {
		Path<?> path = root;
		for (String namePath : namePathString.split("\\.")) {
			path = path.get(namePath);
		}
		return (Path<T>) path;
	}
}
