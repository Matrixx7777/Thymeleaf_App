package com.app.application.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name = "locations")
public class Location{

    @Column(name = "location_name")
    public String location_name;

    @Column(name = "address")
    public String address;

    @Column(name = "ownersShared")
    public String ownersShared;

    public Location(String location_name, String address) {
        this.location_name = location_name;
        this.address = address;
        this.ownersShared = null;
    }
}