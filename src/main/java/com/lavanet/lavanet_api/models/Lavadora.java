package com.lavanet.lavanet_api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lavadoras")
public class Lavadora {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idLavadora;

  private String modelo;
  private String marca;
  private String descripcion;
  private int capacidad;
  private Float precioHora;
  private String estado;

  // Relación con proveedor
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "proveedor_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Usuario proveedor;
  
  // Relación con alquileres
  @OneToMany(mappedBy = "lavadora", orphanRemoval = true)
  private List<Alquiler> alquileres;
}
