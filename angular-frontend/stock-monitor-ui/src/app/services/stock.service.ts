import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {StockListModel} from '../models/stock-list-model';
import {StockPriceModel} from "../models/stock-price-model";
import {Observable, Subject} from "rxjs";
import {StockDetailsModel} from "../models/stock-details-model";

const BASE_URL = 'http://localhost:8080/api/tickers';

@Injectable({
  providedIn: 'root'
})
export class StockService {

  tickerUpdate = new Subject<string>();

  constructor(private http: HttpClient) { }

  getListedStocks(): Observable<StockListModel[]> {
    return this.http.get<StockListModel[]>(BASE_URL);
  }

  getStockData(id: number | undefined): Observable<StockDetailsModel> {
    return this.http.get<StockDetailsModel>(`${BASE_URL}/${id}`);
  }

  getStockPriceData(ticker: string | undefined): Observable<StockPriceModel> {
    return this.http.get<StockPriceModel>(`${BASE_URL}/last/${ticker}`);
  }


}
