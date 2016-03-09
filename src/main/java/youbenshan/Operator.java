package youbenshan;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public enum Operator {
	EQ {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.equal(path, value);
		}
	},
	GT {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.greaterThan(path, value);
		}
	},
	LT {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.lessThan(path, value);
		}
	},
	GTE {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.greaterThanOrEqualTo(path, value);
		}
	},
	LTE {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.lessThanOrEqualTo(path, value);
		}
	},
	STRING_IN {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return path.in(Arrays.stream(((String) value).split(",")).map(x -> x.trim()).collect(Collectors.toSet()));
		}
	},
	LIKE {
		@Override
		public <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
				CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.like(path.as(String.class), (String) value);
		}
	};
	
	public abstract <T extends Comparable<? super T>> Predicate predicate(Path<T> path, T value,
			CriteriaBuilder criteriaBuilder);
}