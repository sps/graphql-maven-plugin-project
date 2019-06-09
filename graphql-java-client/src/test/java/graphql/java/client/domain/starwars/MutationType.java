package graphql.java.client.domain.starwars;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import graphql.java.client.QueryExecutor;
import graphql.java.client.QueryExecutorImpl;
import graphql.java.client.annotation.GraphQLNonScalar;
import graphql.java.client.annotation.GraphQLQuery;
import graphql.java.client.request.Builder;
import graphql.java.client.request.InputParameter;
import graphql.java.client.request.ObjectResponse;
import graphql.java.client.response.GraphQLExecutionException;
import graphql.java.client.response.GraphQLRequestPreparationException;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */
public class MutationType {

	/** Logger for this class */
	private static Logger logger = LogManager.getLogger();

	final QueryExecutor executor;

	/**
	 * This constructor expects the URI of the GraphQL server. This constructor works only for http servers, not for
	 * https ones.<BR/>
	 * For example: https://my.server.com/graphql
	 * 
	 * @param graphqlEndpoint
	 */
	public MutationType(String graphqlEndpoint) {
		this.executor = new QueryExecutorImpl(graphqlEndpoint);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Human}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Human.class)
	@GraphQLQuery
	public Human createHuman(String queryResponseDef, String name, String homePlanet)
			throws GraphQLExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'createHuman' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getCreateHumanResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return createHuman(objectResponse, name, homePlanet);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Human.class)
	@GraphQLQuery
	public Human createHuman(ObjectResponse objectResponse, String name, String homePlanet)
			throws GraphQLExecutionException {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of mutation 'createHuman' with parameters: {}, {} ", name, homePlanet);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of mutation 'createHuman'");
		}

		// InputParameters
		List<InputParameter> parameters = new ArrayList<>();
		parameters.add(new InputParameter("name", name));
		parameters.add(new InputParameter("homePlanet", homePlanet));

		if (!Human.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLExecutionException("The ObjectResponse parameter should be an instance of " + Human.class
					+ ", but is an instance of " + objectResponse.getClass().getName());
		}

		MutationTypeCreateHuman ret = executor.execute("mutation", objectResponse, parameters,
				MutationTypeCreateHuman.class);

		return ret.createHuman;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Human, as expected by the createHuman query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getCreateHumanResponseBuilder() throws GraphQLRequestPreparationException {
		return ObjectResponse.newQueryResponseDefBuilder(getClass(), "createHuman");
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).<BR/>
	 * This method takes care of writting the query name, and the parameter(s) for the query. The given queryResponseDef
	 * describes the format of the response of the server response, that is the expected fields of the {@link Character}
	 * GraphQL type. It can be something like "{ id name }", if you want these fields of this type. Please take a look
	 * at the StarWars, Forum and other samples for more complex queries.
	 * 
	 * @param queryResponseDef
	 *            The response definition of the query, in the native GraphQL format (see here above)
	 * @param episode
	 *            Parameter 1 of this query
	 * @throws IOException
	 * @throws GraphQLRequestPreparationException
	 *             When an error occurs during the request preparation, typically when building the
	 *             {@link ObjectResponse}
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public Character addFriend(String queryResponseDef, String idCharacter, String idNewFriend)
			throws GraphQLExecutionException, GraphQLRequestPreparationException {
		logger.debug("Executing of query 'addFriend' in query mode: {} ", queryResponseDef);
		ObjectResponse objectResponse = getAddFriendResponseBuilder().withQueryResponseDef(queryResponseDef).build();
		return addFriend(objectResponse, idCharacter, idNewFriend);
	}

	/**
	 * This method is expected by the graphql-java framework. It will be called when this query is called. It offers a
	 * logging of the call (if in debug mode), or of the call and its parameters (if in trace mode).
	 * 
	 * @param objectResponse
	 *            The definition of the response format, that describes what the GraphQL server is expected to return
	 * @param episode
	 * @throws IOException
	 * @throws GraphQLExecutionException
	 *             When an error occurs during the request execution, typically a network error, an error from the
	 *             GraphQL server or if the server response can't be parsed
	 */
	@GraphQLNonScalar(graphqlType = Character.class)
	@GraphQLQuery
	public Character addFriend(ObjectResponse objectResponse, String idCharacter, String idNewFriend)
			throws GraphQLExecutionException {
		if (logger.isTraceEnabled()) {
			logger.trace("Executing of mutation 'addFriend' with parameters: {}, {} ", idCharacter, idNewFriend);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Executing of mutation 'addFriend'");
		}

		// InputParameters
		List<InputParameter> parameters = new ArrayList<>();
		parameters.add(new InputParameter("idCharacter", idCharacter));
		parameters.add(new InputParameter("idNewFriend", idNewFriend));

		if (!Character.class.equals(objectResponse.getFieldClass())) {
			throw new GraphQLExecutionException("The ObjectResponse parameter should be an instance of "
					+ Character.class + ", but is an instance of " + objectResponse.getClass().getName());
		}

		MutationTypeAddFriend ret = executor.execute("mutation", objectResponse, parameters,
				MutationTypeAddFriend.class);

		return ret.addFriend;
	}

	/**
	 * Get the {@link ObjectResponse.Builder} for the Character, as expected by the addFriend query.
	 * 
	 * @return
	 * @throws GraphQLRequestPreparationException
	 */
	public Builder getAddFriendResponseBuilder() throws GraphQLRequestPreparationException {
		return ObjectResponse.newQueryResponseDefBuilder(getClass(), "addFriend");
	}

}
