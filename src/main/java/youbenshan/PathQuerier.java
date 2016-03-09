package youbenshan;

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

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<? super T>> Predicate predicate(CriteriaBuilder builder, final Root<?> root,
			Condition condition) {
		Path<?> path = root;
		for (String namePath : condition.getNamePath().split("\\.")) {
			path = path.get(namePath);
		}
		return condition.getOperator().predicate((Path<T>) path, (T) condition.getValue(), builder);
	}
}
