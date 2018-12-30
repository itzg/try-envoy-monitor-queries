package me.itzg.tryenvoymonitorqueries.model;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Geoff Bourne
 * @since Dec 2018
 */
@Data
public class Envoy {

  @Id
  String id;

  @NotEmpty
  List<String> labels;
}
