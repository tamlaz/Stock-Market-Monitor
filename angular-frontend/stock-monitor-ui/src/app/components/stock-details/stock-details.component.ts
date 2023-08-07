import { Component } from '@angular/core';
import {StockListModel} from "../../models/stock-list-model";
import {StockService} from "../../services/stock.service";
import {ActivatedRoute} from "@angular/router";
import {StockPriceModel} from "../../models/stock-price-model";
import {StockDetailsModel} from "../../models/stock-details-model";


@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.css']
})
export class StockDetailsComponent {

  stockId?: number;
  ticker?: string;
  stock!: StockDetailsModel;
  stockPrice!: StockPriceModel;
  intervalId!:any;
  isCompanyDescVisible = false;


  constructor(private stockService: StockService, private activatedRoute: ActivatedRoute) {
      this.activatedRoute.params.subscribe(params => {
        this.stockId = params['id'];
      })
  }

  ngOnInit(){
      this.stockService.getStockData(this.stockId).subscribe({
        next: data => this.stock = data,
        error: err => console.log(err),
        complete: () => {
          this.intervalId = setInterval(() => this.getLastStockPrice(this.stock?.ticker), 60000);
        }
      })
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  getLastStockPrice(ticker: string) {
    this.stockService.getStockPriceData(ticker).subscribe({
      next: data => this.stockPrice = data,
      error: err => console.log(err),
      complete: () => {
        console.log(this.stockPrice);
      }
    })
  }

  toggleDescVisibility() {
    if (this.isCompanyDescVisible) {
      this.isCompanyDescVisible = false;
    } else {
      this.isCompanyDescVisible = true;
    }
  }
}
