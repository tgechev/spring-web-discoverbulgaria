import { Card } from "./Card";

export interface Poi extends Card {
  address?: string;
  longitude?: number;
  latitude?: number;
}
