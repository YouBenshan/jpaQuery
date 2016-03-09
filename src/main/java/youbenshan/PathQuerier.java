package youbenshan;

import java.time.Instant;
import java.time.format.DateTimeParseException;
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
	
	private static <T extends Comparable<? super T>> Predicate predicate(CriteriaBuilder builder, final Root<?> root,
			Condition condition) {
		
		Path<T> path = getPath(root, condition.getNamePath());
		T value = getValue(condition.getValue());
		return condition.getOperator().predicate(path, value, builder);
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<? super T>> T getValue(String value) {
		Object result = null;
		try {
			result = Instant.parse(value);
		} catch (DateTimeParseException e) {
			result = value;
		}
		return (T) result;
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
