package com.graphql_java_generator.client.domain.forum;

/**
 * @author generated by graphql-java-generator
 * @See https://github.com/graphql-java-generator/graphql-java-generator
 */
public class MutationTypeCreateTopic {

	Topic createTopic;

	public void setCreateTopic(Topic createTopic) {
		this.createTopic = createTopic;
	}

	public Topic getCreateTopic() {
		return createTopic;
	}

	@Override
	public String toString() {
		return "MutationTypeCreateTopic {createTopic: " + createTopic + "}";
	}
}
