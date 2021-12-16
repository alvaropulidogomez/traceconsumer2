package es.upm.dit.apsv.traceconsumer2;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.upm.dit.apsv.traceconsumer2.Repository.TransportationOrderRepository;
import es.upm.dit.apsv.traceconsumer2.Repository.TraceRepository;
import es.upm.dit.apsv.traceconsumer2.model.Trace;
import es.upm.dit.apsv.traceconsumer2.model.TransportationOrder;

@SpringBootApplication
public class Traceconsumer2Application {

	public static final Logger log = LoggerFactory.getLogger(Traceconsumer2Application.class);

	@Autowired
	private TraceRepository tr;

	@Autowired
	private TransportationOrderRepository tor;

	public static void main(String[] args) {
		SpringApplication.run(Traceconsumer2Application.class, args);
	}

	@Bean("consumer")
	public Consumer<Trace> checkTrace() {
			return t -> {
					t.setTraceId(t.getTruck() + t.getLastSeen());
					tr.save(t);
					TransportationOrder result = null;
					result = tor.findByTruckAndSt(t.getTruck(), 0);                     
					if (result != null && result.getSt() == 0) {
							result.setLastDate(t.getLastSeen());
							result.setLastLat(t.getLat());
							result.setLastLong(t.getLng());
							if (result.distanceToDestination() < 10)
									result.setSt(1);
							tor.save(result);
					}
			};
	}
}
