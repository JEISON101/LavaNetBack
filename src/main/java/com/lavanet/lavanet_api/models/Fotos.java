package com.lavanet.lavanet_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private Integer idLavadora;
  
  private Integer lavadoraId;
  private String createdAt;

}
