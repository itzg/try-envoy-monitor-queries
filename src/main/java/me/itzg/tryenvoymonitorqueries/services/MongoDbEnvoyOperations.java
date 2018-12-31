package me.itzg.tryenvoymonitorqueries.services;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.client.result.DeleteResult;
import java.util.List;
import me.itzg.tryenvoymonitorqueries.model.Envoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FluentMongoOperations;
import org.springframework.stereotype.Service;

/**
 * @author Geoff Bourne
 * @since Dec 2018
 */
@Service
public class MongoDbEnvoyOperations implements EnvoyOperations {

  private final FluentMongoOperations mongoOperations;

  @Autowired
  public MongoDbEnvoyOperations(FluentMongoOperations mongoOperations) {
    this.mongoOperations = mongoOperations;
  }

  @Override
  public Envoy attach(List<String> labels) {
    return mongoOperations.insert(Envoy.class)
        .one(new Envoy().setLabels(labels));
  }

  @Override
  public Iterable<Envoy> getAll() {
    return mongoOperations.query(Envoy.class)
        .all();
  }

  @Override
  public Iterable<Envoy> getOne(String id) {
    return mongoOperations.query(Envoy.class)
        .matching(query(where("_id").is(id)))
        .all();
  }

  @Override
  public long detachOne(String id) {
    final DeleteResult result = mongoOperations.remove(Envoy.class)
        .matching(query(where("_id").is(id)))
        .one();

    return result.getDeletedCount();
  }

  @Override
  public long detachAll() {
    final DeleteResult result = mongoOperations.remove(Envoy.class)
        .all();

    return result.getDeletedCount();
  }

  @Override
  public List<Envoy> findCandidatesForMonitor(List<String> labels) {
    return mongoOperations.query(Envoy.class)
        .matching(query(where("labels").all(labels)))
        .all();
  }
}
