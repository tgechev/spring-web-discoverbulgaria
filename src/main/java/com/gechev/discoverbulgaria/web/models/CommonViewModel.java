package com.gechev.discoverbulgaria.web.models;

import com.gechev.discoverbulgaria.data.models.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonViewModel extends BaseViewModel {
  private String regionId;

  private String title;

  private String description;

  private Type type;

  private String imageUrl;

  private String readMore;

  private String videoId;
}
