package com.example.msfirstlist.repository.db_repository.db_entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DBRepo {
    @PrimaryKey
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}