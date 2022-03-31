import { Category, Type } from "../../constants";

export interface CurrentRegion {
  regionId: string;
  currentTypeId: string,
  currentCategoryId: string,
  type: Type;
  category: Category;
}
