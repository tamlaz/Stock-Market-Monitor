import {StockPriceModel} from "./stock-price-model";

export interface StockListModel {
  id?: number,
  ticker: string,
  iconUrl: string,
  lastTradeTime: number,
  stockPriceDetails: StockPriceModel;
}
