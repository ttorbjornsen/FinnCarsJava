package ttorbjornsen.finncars.repository;

import ttorbjornsen.finncars.domain.Acq_car_header;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cassandra repository for the Acq_car_header entity.
 */
@Repository
public class Acq_car_headerRepository {

    @Inject
    private Session session;

    private Mapper<Acq_car_header> mapper;

    private PreparedStatement findAllStmt;

    private PreparedStatement truncateStmt;

    @PostConstruct
    public void init() {
        mapper = new MappingManager(session).mapper(Acq_car_header.class);
        findAllStmt = session.prepare("SELECT * FROM acq_car_header");
        truncateStmt = session.prepare("TRUNCATE acq_car_header");
    }

    public List<Acq_car_header> findAll() {
        List<Acq_car_header> acq_car_headersList = new ArrayList<>();
        BoundStatement stmt = findAllStmt.bind();
        session.execute(stmt).all().stream().map(
            row -> {
                Acq_car_header acq_car_header = new Acq_car_header();
                acq_car_header.setId(row.getUUID("id"));
                acq_car_header.setUrl(row.getString("url"));
                acq_car_header.setLoad_date(row.getString("load_date"));
                acq_car_header.setLoad_time(row.getLong("load_time"));
                acq_car_header.setLocation(row.getString("location"));
                acq_car_header.setYear(row.getString("year"));
                acq_car_header.setKm(row.getString("km"));
                acq_car_header.setPrice(row.getString("price"));
                return acq_car_header;
            }
        ).forEach(acq_car_headersList::add);
        return acq_car_headersList;
    }

    public Acq_car_header findOne(UUID id) {
        return mapper.get(id);
    }

    public Acq_car_header save(Acq_car_header acq_car_header) {
        if (acq_car_header.getId() == null) {
            acq_car_header.setId(UUID.randomUUID());
        }
        mapper.save(acq_car_header);
        return acq_car_header;
    }

    public void delete(UUID id) {
        mapper.delete(id);
    }

    public void deleteAll() {
        BoundStatement stmt = truncateStmt.bind();
        session.execute(stmt);
    }
}
