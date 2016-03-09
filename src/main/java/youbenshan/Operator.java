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
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.equal(path, value);
		}
	},
	GT {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.greaterThan(path, value);
		}
	},
	LT {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.lessThan(path, value);
		}
	},
	GTE {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.greaterThanOrEqualTo(path, value);
		}
	},
	LTE {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.lessThanOrEqualTo(path, value);
		}
	},
	DATE_EQ {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.equal(path.as(Date.class), parseDate(value));
		}
	},
	DATE_GT {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.greaterThan(path.as(Date.class), parseDate(value));
		}
	},
	DATE_LT {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.lessThan(path.as(Date.class), parseDate(value));
		}
	},
	DATE_GTE {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.greaterThanOrEqualTo(path.as(Date.class), parseDate(value));
		}
	},
	DATE_LTE {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.lessThanOrEqualTo(path.as(Date.class), parseDate(value));
		}
	},
	IN {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return path.in(Arrays.stream(value.split(",")).map(x->x.trim()).collect(Collectors.toSet()));
		}
	},
	LIKE {
		@Override
		public Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder) {
			return criteriaBuilder.like((Path<String>) path, value);
		}
	};
	protected Date parseDate(String value) {
		return new Date(Long.valueOf(value));
	}

	public abstract Predicate predicate(Path<String> path, String value, CriteriaBuilder criteriaBuilder);
}