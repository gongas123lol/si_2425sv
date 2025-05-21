package isel.sisinf.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TopUpId implements Serializable {
    private LocalDateTime dttopup;
    private Integer card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopUpId)) return false;
        TopUpId that = (TopUpId) o;
        return Objects.equals(dttopup, that.dttopup) &&
                Objects.equals(card, that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dttopup, card);
    }
}
