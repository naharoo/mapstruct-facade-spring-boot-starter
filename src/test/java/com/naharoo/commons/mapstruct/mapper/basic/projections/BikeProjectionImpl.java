package com.naharoo.commons.mapstruct.mapper.basic.projections;

import java.util.Objects;

public class BikeProjectionImpl implements BikeProjection{
  private final String brand;
  private final String size;
  private final long price;

  public BikeProjectionImpl(final String brand, final String size, final long price) {
    this.brand = brand;
    this.size = size;
    this.price = price;
  }

  @Override
  public String getBrand() {
    return brand;
  }

  @Override
  public String getSize() {
    return size;
  }

  @Override
  public long getPrice() {
    return price;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final BikeProjectionImpl that = (BikeProjectionImpl) o;
    return price == that.price && Objects.equals(brand, that.brand)
            && Objects.equals(size, that.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, size, price);
  }
}
