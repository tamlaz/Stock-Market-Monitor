import {StockPriceModel} from "./stock-price-model";

export interface StockDetailsModel {
  ticker:string;
  name:string;
  logoUrl:string;
  iconUrl:string;
  description:string;
  stockPriceDetails: StockPriceModel;
}
