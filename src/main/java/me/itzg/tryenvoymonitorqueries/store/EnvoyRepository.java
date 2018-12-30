package me.itzg.tryenvoymonitorqueries.store;

import me.itzg.tryenvoymonitorqueries.model.Envoy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Geoff Bourne
 * @since Dec 2018
 */
public interface EnvoyRepository extends CrudRepository<Envoy, String> {

}
