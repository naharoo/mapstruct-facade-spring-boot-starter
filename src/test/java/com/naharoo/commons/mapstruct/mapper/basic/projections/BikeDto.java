package com.naharoo.commons.mapstruct.mapper.basic.projections;

import java.util.Objects;

public class BikeDto {
  public String brand;
  public String size;
  public long price;

  public BikeDto() {
    this(null, null, 0);
  }

  public BikeDto(final String brand, final String size, final long price) {
    this.brand = brand;
    this.size = size;
    this.price = price;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(final String brand) {
    this.brand = brand;
  }

  public String getSize() {
    return size;
  }

  public void setSize(final String size) {
    this.size = size;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(final long price) {
    this.price = price;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BikeDto bikeDto = (BikeDto) o;
    return price == bikeDto.price && Objects.equals(brand, bikeDto.brand)
            && Objects.equals(size, bikeDto.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, size, price);
  }
}
