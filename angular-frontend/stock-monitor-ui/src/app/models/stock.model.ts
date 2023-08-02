export interface StockModel {
  id?: number,
  ticker: string,
  name: string,
  description: string,
  logoUrl: string,
  iconUrl: string,
  lastStockPrice: number,
  highPrice: number,
  lowPrice: number,
  openPrice: number,
  previousClosePrice: number,
  lastTradeTime: number

}
