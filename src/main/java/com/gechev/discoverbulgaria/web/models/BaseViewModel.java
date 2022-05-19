package com.gechev.discoverbulgaria.web.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseViewModel {
  private String id;

  private String regionId;

  private String title;

  private String description;

  private Type type;

  private String imageUrl;

  private String readMore;

  private String videoId;
}
