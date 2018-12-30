package me.itzg.tryenvoymonitorqueries.api;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.client.result.DeleteResult;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotation.GraphQLApi;
import java.util.List;
import me.itzg.tryenvoymonitorqueries.model.Envoy;
import me.itzg.tryenvoymonitorqueries.model.ResultCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FluentMongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Geoff Bourne
 * @since Dec 2018
 */
@Service
@GraphQLApi
public class EnvoysApi {

  private final FluentMongoOperations mongoOperations;

  @Autowired
  public EnvoysApi(FluentMongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @GraphQLMutation
  public Envoy attachEnvoy(@GraphQLNonNull List<@GraphQLNonNull String> labels) {
    Assert.notEmpty(labels, "One or more labels are required");

    return mongoOperations.insert(Envoy.class)
        .one(new Envoy().setLabels(labels));
  }

  @GraphQLQuery
  public Iterable<Envoy> envoy() {
    return mongoOperations.query(Envoy.class)
        .all();
  }

  @GraphQLQuery
  public Iterable<Envoy> envoy(String id) {
    return mongoOperations.query(Envoy.class)
        .matching(query(where("_id").is(id)))
        .all();
  }

  @GraphQLMutation
  public ResultCount detachEnvoy(@GraphQLNonNull String id) {
    final DeleteResult result = mongoOperations.remove(Envoy.class)
        .matching(query(where("_id").is(id)))
        .one();

    return new ResultCount().setCount(result.getDeletedCount());
  }

  @GraphQLMutation
  public ResultCount detachAllEnvoys() {
    final DeleteResult result = mongoOperations.remove(Envoy.class)
        .all();

    return new ResultCount().setCount(result.getDeletedCount());
  }
}
