package com.gechev.discoverbulgaria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseData implements Serializable {
  private static final long serialVersionUID = -8524185686815426024L;
  private String responseCode;
  private String responseMsg;
}
