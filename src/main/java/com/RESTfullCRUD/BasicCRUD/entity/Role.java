package com.RESTfullCRUD.BasicCRUD.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Column(unique = true,nullable = false)
    private String roleName;
    private String roleDescription;

    @Override
    public String toString() {
        return this.roleName;
    }
}
