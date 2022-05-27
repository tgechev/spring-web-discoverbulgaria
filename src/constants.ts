import { CloudinaryConfiguration } from '@cloudinary/angular-5.x';

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
  '/facts/edit': 'edit-menu',
  '/poi/edit': 'edit-menu',
  '/regions/edit': 'edit-menu',
  '/facts/add': 'add-menu',
  '/poi/add': 'add-menu',
  '/users/edit': 'edit-users',
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

export const cloudinaryConfig: CloudinaryConfiguration = {
  cloud_name: 'discover-bulgaria',
  api_key: '345473919759429',
};

export const cloudinaryBaseUrl =
  'https://res.cloudinary.com/discover-bulgaria/image/upload/';

export const chooseFactTitle = 'Избери факт';

export const choosePoiTitle = 'Избери забележителност';

export const cloudinaryPresets = {
  facts: 'v6pqytxh',
  poi: 'sgfh2nld',
};
