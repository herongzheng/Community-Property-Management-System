package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.bind.v2.runtime.reflect.Lister;

import javax.persistence.*;
import java.time.LocalDate;

/*
 * The package information is sent by HOA when it creates a new package
 * and received by the resident.
 *
 * HOA manages the statuses of whether the package is pick up or not.
 * If the pickupDate is null, it means it's not picked up yet and if the
 * package is picked up by the right person, HOA can click one "picked up" button.
 *
 * When creating the package info once HOA receives a package, a package record
 * is created in the "package" table and this message is marked as not read. Once
 * the resident reads the message, "is_read" field is set to true.
 *
 * */
@Entity
@Table(name = "package")
public class Package {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @JsonProperty("received_date")
    private LocalDate receivedDate;
    @JsonProperty("pickup_date")
    private LocalDate pickupDate;
    @JsonProperty("message_read")
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User resident;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public User getResident() {
        return resident;
    }

    public Package setDescription(String description) {
        this.description = description;
        return this;
    }

    public Package setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
        return this;
    }

    public Package setRead(boolean read) {
        isRead = read;
        return this;
    }
}
