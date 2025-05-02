package com.fourirbnb.reservation.application.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class CacheResponseDto<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<T> content;
  private int totalPages;
  private long totalElements;
  private int pageNumber;
  private int pageSize;

  public CacheResponseDto(Page<T> page) {
    this.content = page.getContent();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
    this.pageNumber = page.getNumber();
    this.pageSize = page.getSize();
  }
}
