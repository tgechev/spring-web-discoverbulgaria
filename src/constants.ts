export enum Type {
  HISTORY = 'HISTORY',
  NATURE = 'NATURE',
  ALL = 'ALL',
}

export enum Category {
  POI = 'POI',
  FACT = 'FACT',
  ALL = 'ALL',
}

export const ROUTE_TO_NAV_ID: { [key: string]: string } = {
  '/facts/all': 'facts-all',
  '/poi/all': 'poi-all',
  '/home': 'home',
};

export const CARD_CAT_TYPE_ID_TO_ENUM: { [key: string]: Type | Category } = {
  'type-all': Type.ALL,
  'cat-all': Category.ALL,
  poi: Category.POI,
  facts: Category.FACT,
  history: Type.HISTORY,
  nature: Type.NATURE,
};

export enum AnimState {
  SHOW = 'show',
  HIDE = 'hide',
  EXIT = 'exit',
}

export enum UserRole {
  Root = 'ROLE_ROOT',
  Admin = 'ROLE_ADMIN',
  User = 'ROLE_USER',
}
