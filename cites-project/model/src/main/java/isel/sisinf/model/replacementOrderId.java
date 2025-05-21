import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ReplacementOrderId implements Serializable {
    private LocalDateTime dorder;
    private Integer station;

    // equals() and hashCode()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplacementOrderId)) return false;
        ReplacementOrderId that = (ReplacementOrderId) o;
        return Objects.equals(dorder, that.dorder) &&
                Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dorder, station);
    }
}
