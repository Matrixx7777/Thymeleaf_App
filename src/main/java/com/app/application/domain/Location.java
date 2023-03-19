package com.app.application.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
@Table(name = "locations")
public class Location{

    @Column(name = "location_name")
    public String location_name;

    @Column(name = "address")
    public String address;

    @Column(name = "ownersShared")
    public String ownersShared;
}