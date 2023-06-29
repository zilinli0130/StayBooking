//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of StayReservedDateKey class.
//**********************************************************************************************************************

package com.zilinli.staybooking.model;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Embeddable
public class StayReservedDateKey implements Serializable {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public StayReservedDateKey() {}

    public StayReservedDateKey(Long stay_id, LocalDate date) {
        this.stay_id = stay_id;
        this.date = date;
    }

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public Long getStay_id() {
        return stay_id;
    }

    public StayReservedDateKey setStay_id(Long stay_id) {
        this.stay_id = stay_id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public StayReservedDateKey setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stay_id, date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        StayReservedDateKey temp = (StayReservedDateKey) o;
        return this.stay_id.equals(temp.stay_id) && this.date.equals(temp.date);
    }

//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    private Long stay_id;
    private LocalDate date;

    private static final long serialVersionUID = 1L;


}
