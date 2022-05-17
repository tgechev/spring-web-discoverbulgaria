package com.gechev.discoverbulgaria.web.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardViewModel {

  private String id;

  private String title;

  private String description;

  private String address;

  private Type type;

  private String imageUrl;

  private String readMore;

  private String regionId;

  private Double longitude;

  private Double latitude;

  private String videoId;
}
