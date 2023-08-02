import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {StockModel} from '../models/stock.model';
import {StockPrice} from "../models/stock-price.model";
import {Observable, Subject} from "rxjs";

const BASE_URL = 'http://localhost:8080/api/tickers';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  tickerUpdate = new Subject<string>();

  constructor(private http: HttpClient) { }

  getListedStocks(): Observable<StockModel[]> {
    return this.http.get<StockModel[]>(BASE_URL);
  }

  getStockData(id: number | undefined): Observable<StockModel> {
    return this.http.get<StockModel>(`${BASE_URL}/${id}`);
  }

  getStockPriceData(ticker: string | undefined) {
    return this.http.get<StockPrice>(`${BASE_URL}/last/${ticker}`);
  }


}
