package com.springboot.graphql.springbootgraphql;

import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGraphqlApplication.class, args);
	}
}
