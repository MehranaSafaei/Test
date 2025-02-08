package org.example.mehrana.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "`leave`")
@NamedQueries({
        @NamedQuery(name = "countExistsByPersonnelId", query = "SELECT COUNT(l) FROM Leave l WHERE l.personnel.id = :personnelId"),
        @NamedQuery(name = "selectAll", query = "SELECT l FROM Leave l"),
        @NamedQuery(name = "selectByPersonnelIdAndDateRange", query = "SELECT l FROM Leave l WHERE l.personnel.id = :personnelId " +
                "AND (:startDate BETWEEN l.startDate AND l.endDate OR :endDate BETWEEN l.startDate AND l.endDate)"),
        @NamedQuery(name = "selectByPersonnelId", query = "SELECT l FROM Leave l WHERE l.personnel.id = :personnelId"),
        @NamedQuery(name = "findLeavesByPersonnelUsername",query = "SELECT l FROM Leave l WHERE l.personnel.username = :username")
})
public class Leave extends AbstractLeave{

}
