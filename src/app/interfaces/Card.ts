import { Type } from "../../constants";

export interface Card {
  id: number;
  title: string;
  description: string;
  type: Type;
  imageUrl: string;
  readMore: string;
  regionId: string;
  videoId?: string;
}
