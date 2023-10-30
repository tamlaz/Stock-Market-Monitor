import {StockPriceModel} from "./stock-price-model";

export interface StockDetailsModel {
  id:number;
  ticker:string;
  name:string;
  logoUrl:string;
  iconUrl:string;
  description:string;
  stockPriceDetails: StockPriceModel;
}
