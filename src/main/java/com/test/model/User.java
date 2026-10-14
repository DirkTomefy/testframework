package com.test.model;

import com.dirkfw.mapping.DfwMultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20, unique = true)
    private String cin;

    @Column(name = "cin_file_path", length = 255)
    private String cinFilePath;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Transient
    private DfwMultipartFile cinFile;

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getCinFilePath() { return cinFilePath; }
    public void setCinFilePath(String cinFilePath) { this.cinFilePath = cinFilePath; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    @JsonIgnore
    public DfwMultipartFile getCinFile() { return cinFile; }
    public void setCinFile(DfwMultipartFile cinFile) { this.cinFile = cinFile; }
}