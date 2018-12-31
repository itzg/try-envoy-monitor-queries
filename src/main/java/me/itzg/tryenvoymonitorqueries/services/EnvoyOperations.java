package me.itzg.tryenvoymonitorqueries.services;

import java.util.List;
import me.itzg.tryenvoymonitorqueries.model.Envoy;

public interface EnvoyOperations {

  Envoy attach(List<String> labels);

  Iterable<Envoy> getAll();

  Iterable<Envoy> getOne(String id);

  long detachOne(String id);

  long detachAll();

  List<Envoy> findCandidatesForMonitor(List<String> labels);
}
