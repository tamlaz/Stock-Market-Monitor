import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Stock } from '../models/stock.model';

const BASE_URL = 'http://localhost:8080/api/tickers';

@Injectable({
  providedIn: 'root'
})
export class StockServiceService {

  constructor(private http: HttpClient) { }

  getStockDetails() {
    return this.http.get<Stock>(`${BASE_URL}/${ticker}`)
  }
}
