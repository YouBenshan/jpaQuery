package youyu;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PathQuerier {

	private EntityManager entityManager;
	
	public PathQuerier(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public <T > List<T> query(Class<T> EntityClass, Condition... conditions){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(EntityClass);
		final Root<T> root = query.from(EntityClass);
		Predicate[] predicates =Arrays.stream(conditions).map(c ->predicate(builder, root, c)).toArray(Predicate[]::new);
		
		query.where(builder.and(predicates));
		return entityManager.createQuery(query).getResultList();
	
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
