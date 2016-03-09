package youbenshan;

import java.time.Instant;
import java.time.Period;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import youbenshan.User.Sex;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = SimpleConfiguration.class)
public class PathQuerierTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Before
	public void setUp() {

		Role role0 = new Role();
		role0.setName("role0");
		role0.setPriority(1.1f);
		role0 = roleRepository.save(role0);

		Role role1 = new Role();
		role1.setName("role1");
		role1.setPriority(12.1f);
		role1 = roleRepository.save(role1);

		User user0 = new User();
		user0.setName("user0");
		user0.setAge(120);
		user0.setBirthday(Instant.now().minus(Period.ofDays(20 * 365)));
		user0.setRole(role0);
		user0.setSex(Sex.male);
		user0.setGood(true);
		userRepository.save(user0);

		User user1 = new User();
		user1.setName("user1");
		user1.setAge(25);
		user1.setBirthday(Instant.now().minus(Period.ofDays(25 * 365)));
		user1.setRole(role0);
		user1.setSex(Sex.male);
		user1.setGood(true);
		userRepository.save(user1);

		User user2 = new User();
		user2.setName("user2");
		user2.setAge(30);
		user2.setBirthday(Instant.now().minus(Period.ofDays(30 * 365)));
		user2.setRole(role1);
		user2.setSex(Sex.female);
		userRepository.save(user2);
	}

	@Test
	public void testComponse() {

		Condition condition0 = new Condition();
		condition0.setNamePath("age");
		condition0.setOperator(Operator.LT);
		condition0.setValue("50");

		Condition condition1 = new Condition();
		condition1.setNamePath("role.priority");
		condition1.setOperator(Operator.GT);
		condition1.setValue("2.0");

		Assert.assertEquals(1, userRepository.findAll(PathQuerier.query(condition0, condition1)).size());
	}

	@Test
	public void testNumber() {

		Condition condition0 = new Condition();
		condition0.setNamePath("age");
		condition0.setOperator(Operator.LT);
		condition0.setValue("50");
		Assert.assertEquals(2, userRepository.findAll(PathQuerier.query(condition0)).size());

		Condition condition1 = new Condition();
		condition1.setNamePath("priority");
		condition1.setOperator(Operator.GT);
		condition1.setValue("2.0");

		Assert.assertEquals(1, roleRepository.findAll(PathQuerier.query(condition1)).size());
	}
	
	@Test
	public void testBoolean() {

		Condition condition = new Condition();
		condition.setNamePath("good");
		condition.setOperator(Operator.EQ);
		condition.setValue("True");
		List<User> results = userRepository.findAll(PathQuerier.query(condition));
		Assert.assertEquals(2, results.size());
	}
	
	@Test
	public void testEnum() {

		Condition condition = new Condition();
		condition.setNamePath("sex");
		condition.setOperator(Operator.EQ);
		condition.setValue("male");
		List<User> results = userRepository.findAll(PathQuerier.query(condition));
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void testIn() {

		Condition condition = new Condition();
		condition.setNamePath("name");
		condition.setOperator(Operator.STRING_IN);
		condition.setValue("user0, user1");
		List<User> results = userRepository.findAll(PathQuerier.query(condition));
		Assert.assertEquals(2, results.size());
	}

	@Test
	public void testLike() {

		Condition condition = new Condition();
		condition.setNamePath("name");
		condition.setOperator(Operator.LIKE);
		condition.setValue("%2");
		List<User> results = userRepository.findAll(PathQuerier.query(condition));
		Assert.assertEquals(1, results.size());
	}

	@Test
	public void testInstant() {
		Condition condition = new Condition();
		condition.setNamePath("birthday");
		condition.setOperator(Operator.LT);
		// Date date = Date.from(Instant.now().minus(Period.ofDays(26 * 365)));
		// condition.setValue(date.getTime() + "");
		condition.setValue(Instant.now().minus(Period.ofDays(26 * 365)).toString());
		List<User> results = userRepository.findAll(PathQuerier.query(condition));
		Assert.assertEquals(1, results.size());

	}

	@Test
	public void testPageable() {
		Condition condition0 = new Condition();
		condition0.setNamePath("age");
		condition0.setOperator(Operator.LT);
		condition0.setValue("50");
		Page<User> results = userRepository.findAll(PathQuerier.query(condition0), new PageRequest(1, 2));
		Assert.assertEquals(2, results.getTotalElements());
		Assert.assertEquals(1, results.getNumber());
	}

	@Test
	public void testNoneCondition() {
		Page<User> results = userRepository.findAll(PathQuerier.query(new Condition[] {}), new PageRequest(1, 2));
		Assert.assertEquals(3, results.getTotalElements());
		Assert.assertEquals(1, results.getNumber());
	}
}
