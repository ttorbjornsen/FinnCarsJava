package ttorbjornsen.finncars.domain;

import com.datastax.driver.mapping.annotations.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Acq_car_header.
 */

@Table(name = "acq_car_header")
public class Acq_car_header implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    private String url;

    private String load_date;

    private Long load_time;

    private String location;

    private String year;

    private String km;

    private String price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Acq_car_header url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoad_date() {
        return load_date;
    }

    public Acq_car_header load_date(String load_date) {
        this.load_date = load_date;
        return this;
    }

    public void setLoad_date(String load_date) {
        this.load_date = load_date;
    }

    public Long getLoad_time() {
        return load_time;
    }

    public Acq_car_header load_time(Long load_time) {
        this.load_time = load_time;
        return this;
    }

    public void setLoad_time(Long load_time) {
        this.load_time = load_time;
    }

    public String getLocation() {
        return location;
    }

    public Acq_car_header location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYear() {
        return year;
    }

    public Acq_car_header year(String year) {
        this.year = year;
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKm() {
        return km;
    }

    public Acq_car_header km(String km) {
        this.km = km;
        return this;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getPrice() {
        return price;
    }

    public Acq_car_header price(String price) {
        this.price = price;
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Acq_car_header acq_car_header = (Acq_car_header) o;
        if (acq_car_header.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, acq_car_header.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Acq_car_header{" +
            "id=" + id +
            ", url='" + url + "'" +
            ", load_date='" + load_date + "'" +
            ", load_time='" + load_time + "'" +
            ", location='" + location + "'" +
            ", year='" + year + "'" +
            ", km='" + km + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
