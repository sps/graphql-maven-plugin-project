package com.graphql_java_generator.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.graphql_java_generator.client.domain.starwars.Character;
import com.graphql_java_generator.client.domain.starwars.CharacterImpl;
import com.graphql_java_generator.client.domain.starwars.Episode;
import com.graphql_java_generator.client.domain.starwars.Human;
import com.graphql_java_generator.client.domain.starwars.QueryType;
import com.graphql_java_generator.client.domain.starwars.ScalarTest;
import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;

class GraphqlClientUtilsTest {

	GraphqlClientUtils graphqlClientUtils;

	@BeforeEach
	void setUp() throws Exception {
		graphqlClientUtils = new GraphqlClientUtils();
	}

	@Test
	void testCheckName() throws GraphQLRequestPreparationException {
		// Some valid name: we call check, and no exception should be thrown
		graphqlClientUtils.checkName("avalidname");
		graphqlClientUtils.checkName("aValidName");
		graphqlClientUtils.checkName("_aValidName");
		graphqlClientUtils.checkName("ValidName");

		// Various types of checks KO
		assertThrows(NullPointerException.class, () -> graphqlClientUtils.checkName(null));
		assertThrows(GraphQLRequestPreparationException.class, () -> graphqlClientUtils.checkName("qdqd qdsq"));
		assertThrows(GraphQLRequestPreparationException.class, () -> graphqlClientUtils.checkName("qdqd.qdsq"));
		assertThrows(GraphQLRequestPreparationException.class, () -> graphqlClientUtils.checkName("qdqdqdsq."));
		assertThrows(GraphQLRequestPreparationException.class, () -> graphqlClientUtils.checkName(".qdqdqdsq"));
		assertThrows(GraphQLRequestPreparationException.class, () -> graphqlClientUtils.checkName("qdqdqdsq*"));
		assertThrows(GraphQLRequestPreparationException.class, () -> graphqlClientUtils.checkName("qdqdqdsèq"));
	}

	@Test
	void test_checkIsScalar_field() throws NoSuchFieldException, SecurityException, GraphQLRequestPreparationException {

		assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar(ScalarTest.class.getDeclaredField("episode"), false));
		graphqlClientUtils.checkIsScalar(ScalarTest.class.getDeclaredField("episode"), true);

		assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar(Human.class.getDeclaredField("id"), false));
		graphqlClientUtils.checkIsScalar(Human.class.getDeclaredField("id"), true);

		assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar(Human.class.getDeclaredField("name"), false));
		graphqlClientUtils.checkIsScalar(Human.class.getDeclaredField("name"), true);

		assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar(Human.class.getDeclaredField("appearsIn"), false));
		graphqlClientUtils.checkIsScalar(Human.class.getDeclaredField("appearsIn"), true);

	}

	@Test
	void test_checkIsScalar_method()
			throws NoSuchMethodException, SecurityException, GraphQLRequestPreparationException {
		GraphQLRequestPreparationException e;

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar("id", Character.class.getMethod("getId"), false));
		assertTrue(e.getMessage().contains("getId"), "getId");
		assertTrue(e.getMessage().contains("id"), "id");
		graphqlClientUtils.checkIsScalar("id", Character.class.getMethod("getId"), true);

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar("name", Character.class.getMethod("getName"), false));
		assertTrue(e.getMessage().contains("getName"), "getName");
		assertTrue(e.getMessage().contains("name"), "name");
		graphqlClientUtils.checkIsScalar("name", Character.class.getMethod("getName"), true);

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkIsScalar("friends", Character.class.getMethod("getFriends"), true));
		assertTrue(e.getMessage().contains("getFriends"), "getFriends");
		assertTrue(e.getMessage().contains("friends"), "friends");
		graphqlClientUtils.checkIsScalar("friends", Character.class.getMethod("getFriends"), false);
	}

	@Test
	void checkFieldOfGraphQLType_Objets() throws GraphQLRequestPreparationException {
		GraphQLRequestPreparationException e;

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Wrong field names, for interface and classes (shouldBeScalar : null)
		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", null, Character.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", null, CharacterImpl.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", null, Human.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Wrong field names, for interface and classes (shouldBeScalar : false)
		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", false, Character.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", false, CharacterImpl.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", false, Human.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Wrong field names, for interface and classes (shouldBeScalar : true)
		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", true, Character.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", true, CharacterImpl.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", true, Human.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Correct field names (check with wrong scalar type, then correct scalar type). Variation for shouldBeScalar
		// (null or not)
		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("id", false, Character.class));
		assertTrue(e.getMessage().contains("id"), "id");
		//
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", null, Character.class),
				"id : scalar OK");
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", true, Character.class),
				"id : scalar OK");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("id", false, CharacterImpl.class));
		assertTrue(e.getMessage().contains("id"), "id");
		//
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", null, CharacterImpl.class),
				"id : scalar OK");
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", true, CharacterImpl.class),
				"id : scalar OK");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("id", false, Human.class));
		assertTrue(e.getMessage().contains("id"), "id");
		//
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", null, Human.class),
				"id : scalar OK");
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", true, Human.class),
				"id : scalar OK");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("friends", true, Character.class));
		assertTrue(e.getMessage().contains("friends"), "friends");
		//
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", null, Character.class),
				"id : scalar OK");
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", true, Character.class),
				"id : scalar OK");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("friends", true, CharacterImpl.class));
		assertTrue(e.getMessage().contains("friends"), "friends");
		//
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", null, CharacterImpl.class),
				"id : scalar OK");
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", true, CharacterImpl.class),
				"id : scalar OK");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("friends", true, Human.class));
		assertTrue(e.getMessage().contains("friends"), "friends");
		//
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", null, Human.class),
				"id : scalar OK");
		assertEquals(String.class, graphqlClientUtils.checkFieldOfGraphQLType("id", true, Human.class),
				"id : scalar OK");

	}

	@Test
	void checkFieldOfGraphQLType_Query() throws GraphQLRequestPreparationException {
		GraphQLRequestPreparationException e;

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Wrong field name, for interface and classes (shouldBeScalar : null, true, false)
		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", null, QueryType.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", true, QueryType.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("wrong", false, QueryType.class));
		assertTrue(e.getMessage().contains("wrong"), "wrong");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Correct field name, for interface and classes (shouldBeScalar : null, true, false)
		e = assertThrows(GraphQLRequestPreparationException.class,
				() -> graphqlClientUtils.checkFieldOfGraphQLType("hero", true, QueryType.class));
		assertTrue(e.getMessage().contains("hero"), "hero");

		assertEquals(Character.class, graphqlClientUtils.checkFieldOfGraphQLType("hero", null, QueryType.class),
				"hero : scalar OK");
		assertEquals(Character.class, graphqlClientUtils.checkFieldOfGraphQLType("hero", false, QueryType.class),
				"hero : scalar OK");
	}

	@Test
	void test_generatesBindVariableValuesMap() throws GraphQLRequestExecutionException {
		Object[] objects = { "1", "2", "3" };
		Map<String, Object> map;

		map = graphqlClientUtils.generatesBindVariableValuesMap(null);
		assertNotNull(map);
		assertEquals(0, map.size(), "The map is empty");

		// Check that there is an even number of parameters
		assertThrows(GraphQLRequestExecutionException.class,
				() -> graphqlClientUtils.generatesBindVariableValuesMap(objects),
				"There must be an even number of parameters, in series 1");

		// Check ClassCastException
		Object[] objects2 = { "param1", 2, "param2", Episode.JEDI, 3, "a String" };
		assertThrows(ClassCastException.class, () -> graphqlClientUtils.generatesBindVariableValuesMap(objects2));

		// Check normal behavior
		Object[] objects3 = { "param1", 2, "param2", Episode.JEDI, "param3", "a String" };
		map = graphqlClientUtils.generatesBindVariableValuesMap(objects3);
		//
		assertEquals(3, map.size());
		assertEquals(2, map.get("param1"));
		assertEquals(Episode.JEDI, map.get("param2"));
		assertEquals("a String", map.get("param3"));
	}
}
