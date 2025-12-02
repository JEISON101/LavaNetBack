package com.lavanet.lavanet_api.models;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fotos {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idFoto;
  
  private String urlFoto;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Timestamp createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lavadora_id", nullable = false)
  @JsonBackReference // ← ESTO EVITA QUE SE SERIALICE LA LAVADORA DENTRO DE FOTOS
  private Lavadora lavadora;
}