import {StockListItemModel} from "./stock-list-item-model";

export interface UserProfileDetailsModel {
  firstName:string;
  lastName:string;
  email:string;
  watchList: StockListItemModel[];
}
