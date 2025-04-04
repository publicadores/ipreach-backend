package app.ipreach.backend.shifts.db.model;

import app.ipreach.backend.locations.db.model.Location;
import app.ipreach.backend.shared.enums.EStatus;
import app.ipreach.backend.users.db.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
@Table(name = "shift-requests")
public class ShiftRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Shift shift;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "partnerShiftRequest",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<User> partners;

    @NotNull
    private int slotsRequested;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EStatus status;

}
