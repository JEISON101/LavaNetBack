package com.lavanet.lavanet_api.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
  private int stock;
  private Float precioHora;
  private String estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "proveedor_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "lavadoras", "favoritosCliente", "alquileres", "ubicaciones"})
  private Usuario proveedor;
  
  @OneToMany(mappedBy = "lavadora", orphanRemoval = true)
  @JsonIgnoreProperties("lavadora") // ← Ignora la referencia circular en alquileres
  private List<Alquiler> alquileres;

  @OneToMany(
    mappedBy = "lavadora", 
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  @JsonManagedReference // ← ESTO EVITA LA REFERENCIA CIRCULAR
  private List<Fotos> fotos = new ArrayList<>();
}