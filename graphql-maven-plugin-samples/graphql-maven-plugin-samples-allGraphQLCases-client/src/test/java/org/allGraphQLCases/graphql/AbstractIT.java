package org.allGraphQLCases.graphql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.allGraphQLCases.Queries;
import org.allGraphQLCases.client.AllFieldCases;
import org.allGraphQLCases.client.AllFieldCasesInput;
import org.allGraphQLCases.client.Character;
import org.allGraphQLCases.client.CharacterInput;
import org.allGraphQLCases.client.Episode;
import org.allGraphQLCases.client.FieldParameterInput;
import org.allGraphQLCases.client.MyQueryType;
import org.junit.jupiter.api.Test;

import com.graphql_java_generator.exception.GraphQLRequestExecutionException;
import com.graphql_java_generator.exception.GraphQLRequestPreparationException;

/**
 * As it is suffixed by "IT", this is an integration test. Thus, it allows us to start the GraphQL StatWars server, see
 * the pom.xml file for details.
 * 
 * @author EtienneSF
 */
abstract class AbstractIT {

	MyQueryType queryType;
	Queries queries;

	@Test
	void test_withoutParameters() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		List<Character> list = queries.withoutParameters();

		assertNotNull(list);
		assertEquals(10, list.size());
		for (Character c : list) {
			checkCharacter(c, "withoutParameters", true, "Random String (", 0, 0);
		}
	}

	@Test
	void test_withOneOptionalParam() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// Without parameter
		Character c = queries.withOneOptionalParam(null);
		checkCharacter(c, "test_withOneOptionalParam(null)", false, "Random String (", 0, 0);

		// With a parameter
		CharacterInput input = new CharacterInput();
		input.setName("A name");
		input.setAppearsIn(new ArrayList<Episode>());

		// Go, go, go
		c = queries.withOneOptionalParam(input);
		// Verification
		assertNotNull(c.getId());
		assertEquals("A name", c.getName());

		// appearsIn and friends is generated on server side.
		assertNotNull(c.getAppearsIn());
		assertEquals(2, c.getAppearsIn().size()); // See DataFetchersDelegateCharacterImpl.appearsIn
		assertNotNull(c.getFriends());
		assertEquals(4, c.getFriends().size());// See DataFetchersDelegateCharacterImpl.friends
	}

	@Test
	void test_withOneMandatoryParam_nullParameter()
			throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		// With null parameter
		GraphQLRequestExecutionException e = assertThrows(GraphQLRequestExecutionException.class,
				() -> queries.withOneMandatoryParam(null));
		assertTrue(e.getMessage().contains("character"));
	}

	@Test
	void test_withOneMandatoryParam_OK() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		// With a non null parameter
		CharacterInput input = new CharacterInput();
		input.setName("A name");
		input.setAppearsIn(new ArrayList<Episode>());

		// Go, go, go
		Character c = queries.withOneMandatoryParam(input);

		// Verification
		assertNotNull(c.getId());
		assertEquals("A name", c.getName());

		// appearsIn and friends is generated on server side.
		assertNotNull(c.getAppearsIn());
		assertEquals(2, c.getAppearsIn().size()); // See DataFetchersDelegateCharacterImpl.appearsIn
		assertNotNull(c.getFriends());
		assertEquals(4, c.getFriends().size());// See DataFetchersDelegateCharacterImpl.friends
	}

	@Test
	void test_withEnum() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {

		// With null parameter: NEWHOPE is the default value
		Character c = queries.withEnum(null);
		assertEquals(Episode.NEWHOPE.name(), c.getName());// See server code for more info

		// With a non null parameter
		c = queries.withEnum(Episode.JEDI);
		assertEquals(Episode.JEDI.name(), c.getName()); // See server code for more info
	}

	@Test
	void test_withList() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		// Preparation
		CharacterInput ci1 = new CharacterInput();
		ci1.setName("A name");
		ci1.setAppearsIn(new ArrayList<Episode>());
		//
		CharacterInput ci2 = new CharacterInput();
		ci2.setName("Another name");
		ci2.setAppearsIn(new ArrayList<Episode>());
		//
		List<CharacterInput> list = new ArrayList<CharacterInput>();
		list.add(ci1);
		list.add(ci2);
		//
		String firstName = "A first name";

		// Go, go, go
		List<Character> ret = queries.withList(firstName, list);

		// Verification
		assertEquals(2, ret.size());
		//
		int i = 0;
		assertNotNull(ret.get(i).getId());
		assertEquals(firstName, ret.get(i).getName());
		//
		i += 1;
		assertNotNull(ret.get(i).getId());
		assertEquals("Another name", ret.get(i).getName());

	}

	@Test
	void test_allFieldCases() throws GraphQLRequestExecutionException, GraphQLRequestPreparationException {
		// Not implemented in direct queries
		if (this.getClass().getSimpleName().equals("DirectQueriesIT")) {

			// Go, go, go
			// bind variables are not used in DirectQueries
			GraphQLRequestPreparationException e = assertThrows(GraphQLRequestPreparationException.class,
					() -> queries.allFieldCases(null, null, null, 3, null, null, null, null, null, 2, null, null));

			assertTrue(e.getMessage().contains("listWithIdSubTypes"));
			assertTrue(e.getMessage().contains("input parameters are not managed in Direct Queries"));

		} else {

			// Preparation
			AllFieldCasesInput allFieldCasesInput = null;
			Boolean uppercase = true;
			String textToAppendToTheForname = "textToAppendToTheForname";
			long nbItemsWithId = 3;
			Date date = new Date(2000 - 1900, 12 - 1, 20);
			Date date2 = new Date(2000 - 1900, 12 - 1, 21);
			List<Date> dates = new ArrayList<>();
			dates.add(date);
			dates.add(date2);
			Boolean uppercaseNameList = null;
			String textToAppendToTheFornameWithId = "textToAppendToTheFornameWithId";
			FieldParameterInput input = new FieldParameterInput();
			input.setUppercase(true);
			int nbItemsWithoutId = 6;
			FieldParameterInput inputList = null;
			String textToAppendToTheFornameWithoutId = "textToAppendToTheFornameWithoutId";

			// Go, go, go
			AllFieldCases allFieldCases = queries.allFieldCases(allFieldCasesInput, uppercase, textToAppendToTheForname,
					nbItemsWithId, date, dates, uppercaseNameList, textToAppendToTheFornameWithId, input,
					nbItemsWithoutId, inputList, textToAppendToTheFornameWithoutId);

			// Verification
			assertNotNull(allFieldCases);

			// listWithIdSubTypes
			assertEquals(nbItemsWithId, allFieldCases.getListWithIdSubTypes().size());
			assertTrue(allFieldCases.getListWithIdSubTypes().get(0).getName().endsWith(textToAppendToTheFornameWithId));
			// listWithoutIdSubTypes
			assertEquals(nbItemsWithoutId, allFieldCases.getListWithoutIdSubTypes().size());
			assertTrue(allFieldCases.getListWithoutIdSubTypes().get(0).getName()
					.endsWith(textToAppendToTheFornameWithoutId));
		}
	}

	@Test
	void test_error() {
		GraphQLRequestExecutionException e = assertThrows(GraphQLRequestExecutionException.class,
				() -> queries.error("This is an expected error"));
		assertTrue(e.getMessage().contains("This is an expected error"),
				"'" + e.getMessage() + "' should contain 'This is an expected error'");
	}

	private void checkCharacter(Character c, String testDecription, boolean idShouldBeNull, String nameStartsWith,
			int nbFriends, int nbAppearsIn) {

		if (idShouldBeNull)
			assertNull(c.getId(), testDecription + " (id)");
		else
			assertNotNull(c.getId(), testDecription + " (id)");

		assertTrue(c.getName().startsWith(nameStartsWith),
				testDecription + " (name starts with " + nameStartsWith + ")");

		// nbFriends is the number of friends... before any call to addFriend
		if (nbFriends == 0) {
			// c.getFriends() may be null
			if (c.getFriends() != null) {
				assertTrue(c.getFriends().size() >= 0, testDecription + " (friends)");
			}
		} else {
			assertTrue(c.getFriends().size() >= nbFriends, testDecription + " (friends)");
			for (Character friend : c.getFriends()) {
				// Expected fields: id and name
				assertNotNull(friend.getId());
				assertNotNull(friend.getName());
				assertNull(friend.getAppearsIn());
			}
		}

		// nbEpisodes is the expected number of episodes
		if (nbAppearsIn == 0) {
			// c.getAppearsIn() may be null
			if (c.getAppearsIn() != null) {
				assertTrue(c.getAppearsIn().size() >= 0, testDecription + " (getAppearsIn)");
			}
		} else {
			assertTrue(c.getAppearsIn().size() >= nbAppearsIn, testDecription + " (getAppearsIn)");
		}

	}
}
