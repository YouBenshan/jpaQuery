package youbenshan;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class PathQuerier {
	public <T> Specification<T> query(final Condition... conditions){
		return new Specification<T>(){
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return Arrays.stream(conditions).map(c ->predicate(cb, root, c)).reduce(cb::and).orElseGet(cb::conjunction);
			}};
	}

	private Predicate predicate(CriteriaBuilder builder, final Root<?> root,
			Condition condition) {
		String[] namePath = condition.getNamePath().split("\\.");
		Path<String> path = root.get(namePath[0]);
		for (int i = 1; i < namePath.length; i++) {
			path = path.get(namePath[i]);
		}
		return condition.getOperator().predicate(path, condition.getValue(),
				builder);
	}
}
