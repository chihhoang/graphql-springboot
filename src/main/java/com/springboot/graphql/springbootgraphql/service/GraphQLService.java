package com.springboot.graphql.springbootgraphql.service;

import com.springboot.graphql.springbootgraphql.model.Book;
import com.springboot.graphql.springbootgraphql.repository.BookRepository;
import com.springboot.graphql.springbootgraphql.service.datafetcher.AllBooksDataFetcher;
import com.springboot.graphql.springbootgraphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    private Resource resource;

    @Autowired
    private BookRepository bookRepository;

    private GraphQL graphQL;

    public GraphQL getGraphQL() {
        return graphQL;
    }

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;

    // load schema at start-up
    @PostConstruct
    public void loadSchema() throws IOException {
        loadDataIntoDB();

        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoDB() {

        Stream.of(
                new Book("1", "Java", "Kindle", new String[] {"John Doe", "Adam Smith"}, "March 22 2012"),
                new Book("2", "Cloud Computing", "Cloud", new String[] {"Jane K"}, "April 02 2014"),
                new Book("3", "Data Science", "Orreily", new String[] {"Tyler Johnson"}, "May 22 2018")
                )
                .forEach(book -> bookRepository.save(book));
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                    typeWiring
                            .dataFetcher("allBooks", allBooksDataFetcher)
                            .dataFetcher("book", bookDataFetcher)
                )
                .build();
    }
}
