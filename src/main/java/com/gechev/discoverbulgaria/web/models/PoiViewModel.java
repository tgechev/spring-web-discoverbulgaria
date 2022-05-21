package com.gechev.discoverbulgaria.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PoiViewModel extends CommonViewModel {
  private String address;

  private Double longitude;

  private Double latitude;
}
