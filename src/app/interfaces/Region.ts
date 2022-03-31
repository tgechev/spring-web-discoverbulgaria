import { Poi } from "./Poi";
import { Fact } from "./Fact";
export interface Region {
  regionId: string;
  name: string;
  population: number;
  area: number;
  imageUrl: string;
  // pois: Poi[];
  // facts: Fact[];
}
