package es.upm.dit.apsv.traceconsumer2.Repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.apsv.traceconsumer2.model.Trace;


public interface TraceRepository extends CrudRepository<Trace,String> {

}
