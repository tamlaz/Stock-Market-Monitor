import {StockPriceModel} from "./stock-price-model";

export interface StockListItemModel {
  id?: number,
  ticker: string,
  iconUrl: string,
  lastTradeTime: number,
  stockPriceDetails: StockPriceModel;
}
