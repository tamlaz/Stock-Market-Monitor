import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {StockListModel} from '../models/stock-list-model';
import {StockPriceModel} from "../models/stock-price-model";
import {Observable, Subject} from "rxjs";
import {StockDetailsModel} from "../models/stock-details-model";
import {environment} from "../../environments/environment.development";

const STOCK_BASE_URL = environment.STOCK_BASE_URL;

@Injectable({
  providedIn: 'root'
})
export class StockService {


  constructor(private http: HttpClient) { }

  getListedStocks(): Observable<StockListModel[]> {
    return this.http.get<StockListModel[]>(STOCK_BASE_URL);
  }

  getStockData(id: number | undefined): Observable<StockDetailsModel> {
    return this.http.get<StockDetailsModel>(`${STOCK_BASE_URL}/${id}`);
  }

  getStockPriceData(ticker: string | undefined): Observable<StockPriceModel> {
    return this.http.get<StockPriceModel>(`${STOCK_BASE_URL}/last/${ticker}`);
  }

  addStock(ticker: string) {
    return this.http.post(`${STOCK_BASE_URL}/${ticker}`,null);
  }


}
