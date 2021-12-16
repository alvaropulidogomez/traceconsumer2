package es.upm.dit.apsv.traceconsumer2.controller;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.apsv.traceconsumer2.Repository.TransportationOrderRepository;
import es.upm.dit.apsv.traceconsumer2.model.TransportationOrder;

@RestController
public class TransportationOrderController {
    private final TransportationOrderRepository repository;

    public static final Logger log = LoggerFactory.getLogger(TransportationOrderController.class);


    public TransportationOrderController(TransportationOrderRepository repository) {
      this.repository = repository;
    }
  
    @GetMapping("/orders")
    List<TransportationOrder> all() {
      return (List<TransportationOrder>) repository.findAll();
    }
  
    
    @GetMapping("/orders/truck/{id}")
    TransportationOrder getActiveTruckOrder(@PathVariable String id) {
      log.info("Truck:" + id);
      return repository.findByTruckAndSt(id, 0);
    }


    @PostMapping("/orders")
    TransportationOrder newOrder(@RequestBody TransportationOrder newOrder) {
      return repository.save(newOrder);
    }
    
    @GetMapping("/orders/{truck}")
    ResponseEntity<TransportationOrder> getByTruck(@PathVariable String truck) {
      Optional<TransportationOrder> ot = repository.findById(truck);
      if (ot.isPresent()) 
        return new ResponseEntity<>(ot.get(), HttpStatus.OK);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  
    @PutMapping("/orders")
    ResponseEntity<TransportationOrder> update(@RequestBody TransportationOrder updatedOrder) {
      TransportationOrder to = repository.save(updatedOrder);
      if (to == null) 
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return new ResponseEntity<>(to, HttpStatus.OK);
      //       return repository.save(updatedOrder).orElseThrow();
    }
  
    @DeleteMapping("/orders/{truck}")
    void deleteOrder(@PathVariable String truck) {
      repository.deleteById(truck);
    }    
}
