//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 04/23
// * Definition: Implementation of Reservation class.
//**********************************************************************************************************************

package com.zilinli.staybooking.model;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// Java includes
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Entity
@Table(name="reservation")
@JsonDeserialize(builder=Reservation.Builder.class)
public class Reservation implements Serializable {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public Reservation() { }
    private Reservation(Builder builder) {
        this.id = builder.id;
        this.checkinDate = builder.checkinDate;
        this.checkoutDate = builder.checkoutDate;
        this.guest = builder.guest;
        this.stay = builder.stay;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public Long getId() {
        return id;
    }

    public Reservation setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public Reservation setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
        return this;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public Reservation setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
        return this;
    }

    public User getGuest() {
        return guest;
    }

    public Reservation setGuest(User guest) {
        this.guest = guest;
        return this;
    }

    public Stay getStay() {
        return stay;
    }

    public Reservation setStay(Stay stay) {
        this.stay = stay;
        return this;
    }


//**********************************************************************************************************************
// * Public inner class
//**********************************************************************************************************************

    public static class Builder {

        @JsonProperty("id")
        private Long id;
        @JsonProperty("checkin_date")
        private LocalDate checkinDate;
        @JsonProperty("checkout_date")
        private LocalDate checkoutDate;
        @JsonProperty("guest")
        private User guest;
        @JsonProperty("stay")
        private Stay stay;

        public Long getId() {
            return id;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public LocalDate getCheckinDate() {
            return checkinDate;
        }

        public Builder setCheckinDate(LocalDate checkinDate) {
            this.checkinDate = checkinDate;
            return this;
        }

        public LocalDate getCheckoutDate() {
            return checkoutDate;
        }

        public Builder setCheckoutDate(LocalDate checkoutDate) {
            this.checkoutDate = checkoutDate;
            return this;
        }

        public User getGuest() {
            return guest;
        }

        public Builder setGuest(User guest) {
            this.guest = guest;
            return this;
        }

        public Stay getStay() {
            return stay;
        }

        public Builder setStay(Stay stay) {
            this.stay = stay;
            return this;
        }

        public Reservation build() {
            return new Reservation(this);
        }
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("checkin_date")
    private LocalDate checkinDate;
    @JsonProperty("checkout_date")
    private LocalDate checkoutDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User guest; // Connect FK
    @ManyToOne
    @JoinColumn(name="stay_id")
    private Stay stay; // Connect FK

    private static final long serialVersionUID = 1L;
}
