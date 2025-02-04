package org.example.mehrana.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Leave")
@NamedQueries({@NamedQuery(name = "CountExistsByPersonnelId",query = "SELECT COUNT(l) FROM Leave l WHERE l.personnel.id = :personnelId"),
        @NamedQuery(name = "SelectAll", query = "SELECT l FROM Leave l"),
        @NamedQuery(name = "selectByPersonnelIdAndDateRange", query ="SELECT l FROM Leave l WHERE l.personnel.id = :personnelId AND "+
        "(:startDate BETWEEN l.startDate AND l.endDate OR " +
        ":endDate BETWEEN l.startDate AND l.endDate")
})
public class Leave extends AbstractLeave{

}
