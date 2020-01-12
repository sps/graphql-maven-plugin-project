package org.allGraphQLCases.server.impl;

import java.util.List;

import org.allGraphQLCases.server.Character;
import org.allGraphQLCases.server.CharacterInput;
import org.allGraphQLCases.server.DataFetchersDelegateMyQueryType;
import org.allGraphQLCases.server.Droid;
import org.allGraphQLCases.server.DroidInput;
import org.allGraphQLCases.server.Episode;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author EtienneSF
 *
 */
@Component
public class DataFetchersDelegateMyQueryTypeImpl implements DataFetchersDelegateMyQueryType {

	@Override
	public List<Character> withoutParameters(DataFetchingEnvironment dataFetchingEnvironment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character withOneOptionalParam(DataFetchingEnvironment dataFetchingEnvironment, CharacterInput character) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character withOneMandatoryParam(DataFetchingEnvironment dataFetchingEnvironment, CharacterInput character) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character withOneMandatoryParamDefaultValue(DataFetchingEnvironment dataFetchingEnvironment,
			CharacterInput character) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Droid withTwoMandatoryParamDefaultVal(DataFetchingEnvironment dataFetchingEnvironment, DroidInput theHero,
			Integer index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Character withEnum(DataFetchingEnvironment dataFetchingEnvironment, Episode episode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Character> withList(DataFetchingEnvironment dataFetchingEnvironment, String name,
			List<CharacterInput> friends) {
		// TODO Auto-generated method stub
		return null;
	}

}