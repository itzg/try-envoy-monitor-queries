package me.itzg.tryenvoymonitorqueries.api;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import java.util.List;
import me.itzg.tryenvoymonitorqueries.model.Envoy;
import me.itzg.tryenvoymonitorqueries.model.ResultCount;
import me.itzg.tryenvoymonitorqueries.services.EnvoyOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Provides the API operations for managing fake Envoy entries.
 */
@Service
@GraphQLApi
public class EnvoysApi {

  private final EnvoyOperations envoyOperations;

  @Autowired
  public EnvoysApi(EnvoyOperations envoyOperations) {
    this.envoyOperations = envoyOperations;
  }

  @GraphQLMutation(description = "Creates a fake entry in the system that would represent an attached Envoy")
  public Envoy attachEnvoy(@GraphQLNonNull List<@GraphQLNonNull String> labels) {
    Assert.notEmpty(labels, "One or more labels are required");

    return envoyOperations.attach(labels);
  }

  @GraphQLQuery
  public Iterable<Envoy> envoy() {
    return envoyOperations.getAll();
  }

  @GraphQLQuery
  public Iterable<Envoy> envoy(String id) {
    return envoyOperations.getOne(id);
  }

  @GraphQLMutation
  public ResultCount detachEnvoy(@GraphQLNonNull String id) {
    final long count = envoyOperations.detachOne(id);
    return new ResultCount().setCount(count);
  }

  @GraphQLMutation
  public ResultCount detachAllEnvoys() {
    final long count = envoyOperations.detachAll();
    return new ResultCount().setCount(count);
  }
}
