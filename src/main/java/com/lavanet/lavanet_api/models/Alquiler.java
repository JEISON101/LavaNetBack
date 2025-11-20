package com.lavanet.lavanet_api.models;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alquileres")
public class Alquiler {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idAlquiler;
  private String estado;
  private Float total;
  private Timestamp fechaInicio;
  private Timestamp fechaFin;
  private String direccionEntrega;
  private String formaPago;
  private int catidad;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Timestamp created;

  //relacion con el cliente
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cliente_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Usuario cliente;

  // Relación con lavadora
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lavadora_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Lavadora lavadora;
  
}