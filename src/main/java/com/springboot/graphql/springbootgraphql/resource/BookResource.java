package com.springboot.graphql.springbootgraphql.resource;

import com.springboot.graphql.springbootgraphql.service.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/books")
@RestController
public class BookResource {

    @Autowired
    private GraphQLService graphQLService;

    @PostMapping
    public ResponseEntity<?> getALlBooks(@RequestBody String query) {
        ExecutionResult result = graphQLService.getGraphQL().execute(query);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
