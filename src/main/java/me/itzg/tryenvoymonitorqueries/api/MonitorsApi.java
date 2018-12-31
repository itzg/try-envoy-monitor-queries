package me.itzg.tryenvoymonitorqueries.api;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import java.util.List;
import me.itzg.tryenvoymonitorqueries.model.Envoy;
import me.itzg.tryenvoymonitorqueries.services.EnvoyOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FluentMongoOperations;
import org.springframework.stereotype.Service;

/**
 * @author Geoff Bourne
 * @since Dec 2018
 */
@Service
@GraphQLApi
public class MonitorsApi {

  private final EnvoyOperations envoyOperations;

  @Autowired
  public MonitorsApi(EnvoyOperations envoyOperations) {
    this.envoyOperations = envoyOperations;
  }

  @GraphQLQuery
  public List<Envoy> findCandidatesForMonitor(@GraphQLNonNull List<@GraphQLNonNull String> labels) {
    return envoyOperations.findCandidatesForMonitor(labels);
  }
}
