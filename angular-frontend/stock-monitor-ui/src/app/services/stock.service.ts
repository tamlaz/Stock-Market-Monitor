import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Stock } from '../models/stock.model';
import {StockPrice} from "../models/stock-price.model";
import {Subject} from "rxjs";

const BASE_URL = 'http://localhost:8080/api/tickers';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  tickerUpdate = new Subject<string>();

  constructor(private http: HttpClient) { }

  getListedStocks() {
    return this.http.get<Stock[]>(BASE_URL);
  }

  getStockData(id: number | undefined) {
    return this.http.get<Stock>(`${BASE_URL}/${id}`);
  }

  getStockPriceData(ticker: string | undefined) {
    return this.http.get<StockPrice>(`${BASE_URL}/last/${ticker}`);
  }


}
