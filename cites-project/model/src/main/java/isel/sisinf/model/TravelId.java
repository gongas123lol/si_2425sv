package isel.sisinf.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TravelId implements Serializable {
    private LocalDateTime dinitial;
    private Integer scooter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelId)) return false;
        TravelId that = (TravelId) o;
        return Objects.equals(dinitial, that.dinitial) &&
                Objects.equals(scooter, that.scooter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dinitial, scooter);
    }
}
