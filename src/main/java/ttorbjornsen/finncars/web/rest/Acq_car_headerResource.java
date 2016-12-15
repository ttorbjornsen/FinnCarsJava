package ttorbjornsen.finncars.web.rest;

import com.codahale.metrics.annotation.Timed;
import ttorbjornsen.finncars.domain.Acq_car_header;

import ttorbjornsen.finncars.repository.Acq_car_headerRepository;
import ttorbjornsen.finncars.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Acq_car_header.
 */
@RestController
@RequestMapping("/api")
public class Acq_car_headerResource {

    private final Logger log = LoggerFactory.getLogger(Acq_car_headerResource.class);
        
    @Inject
    private Acq_car_headerRepository acq_car_headerRepository;

    /**
     * POST  /acq-car-headers : Create a new acq_car_header.
     *
     * @param acq_car_header the acq_car_header to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acq_car_header, or with status 400 (Bad Request) if the acq_car_header has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acq-car-headers")
    @Timed
    public ResponseEntity<Acq_car_header> createAcq_car_header(@RequestBody Acq_car_header acq_car_header) throws URISyntaxException {
        log.debug("REST request to save Acq_car_header : {}", acq_car_header);
        if (acq_car_header.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("acq_car_header", "idexists", "A new acq_car_header cannot already have an ID")).body(null);
        }
        Acq_car_header result = acq_car_headerRepository.save(acq_car_header);
        return ResponseEntity.created(new URI("/api/acq-car-headers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("acq_car_header", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acq-car-headers : Updates an existing acq_car_header.
     *
     * @param acq_car_header the acq_car_header to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acq_car_header,
     * or with status 400 (Bad Request) if the acq_car_header is not valid,
     * or with status 500 (Internal Server Error) if the acq_car_header couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acq-car-headers")
    @Timed
    public ResponseEntity<Acq_car_header> updateAcq_car_header(@RequestBody Acq_car_header acq_car_header) throws URISyntaxException {
        log.debug("REST request to update Acq_car_header : {}", acq_car_header);
        if (acq_car_header.getId() == null) {
            return createAcq_car_header(acq_car_header);
        }
        Acq_car_header result = acq_car_headerRepository.save(acq_car_header);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("acq_car_header", acq_car_header.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acq-car-headers : get all the acq_car_headers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acq_car_headers in body
     */
    @GetMapping("/acq-car-headers")
    @Timed
    public List<Acq_car_header> getAllAcq_car_headers() {
        log.debug("REST request to get all Acq_car_headers");
        List<Acq_car_header> acq_car_headers = acq_car_headerRepository.findAll();
        return acq_car_headers;
    }

    /**
     * GET  /acq-car-headers/:id : get the "id" acq_car_header.
     *
     * @param id the id of the acq_car_header to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acq_car_header, or with status 404 (Not Found)
     */
    @GetMapping("/acq-car-headers/{id}")
    @Timed
    public ResponseEntity<Acq_car_header> getAcq_car_header(@PathVariable String id) {
        log.debug("REST request to get Acq_car_header : {}", id);
        Acq_car_header acq_car_header = acq_car_headerRepository.findOne(UUID.fromString(id));
        return Optional.ofNullable(acq_car_header)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /acq-car-headers/:id : delete the "id" acq_car_header.
     *
     * @param id the id of the acq_car_header to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acq-car-headers/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcq_car_header(@PathVariable String id) {
        log.debug("REST request to delete Acq_car_header : {}", id);
        acq_car_headerRepository.delete(UUID.fromString(id));
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("acq_car_header", id.toString())).build();
    }

}
