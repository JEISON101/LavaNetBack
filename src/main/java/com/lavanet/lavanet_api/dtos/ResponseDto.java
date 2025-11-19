package com.lavanet.lavanet_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean success;
  private String message;
  private T data;
  private String error;
}