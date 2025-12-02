package com.lavanet.lavanet_api.models;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String direccion;
    private String correo;
    private String password;
    private String telefono;
    
    private String rol;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created;

    // Un proveedor puede tener muchas lavadoras
    @OneToMany(mappedBy = "proveedor", orphanRemoval = true)
    private List<Lavadora> lavadoras;

    // Un cliente puede tener muchos favoritos
    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private List<Favoritos> favoritosCliente;

    // Un cliente puede tener muchos alquileres
    @OneToMany(mappedBy = "cliente", orphanRemoval = true)
    private List<Alquiler> alquileres;

    //relacion con las ubicaciones para cliente y proveedor
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ubicacion> ubicaciones;

}
