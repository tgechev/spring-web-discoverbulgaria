package com.gechev.discoverbulgaria.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteModel extends BaseViewModel {
  boolean isDeleted;
}
