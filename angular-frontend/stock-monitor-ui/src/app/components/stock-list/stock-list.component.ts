import { Component } from '@angular/core';
import {StockListModel} from 'src/app/models/stock-list-model';
import { StockService } from 'src/app/services/stock.service';
import {Router} from "@angular/router";
import {StockPriceModel} from "../../models/stock-price-model";
import {faArrowDown, faArrowUp} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent {

  arrowUp=faArrowUp;
  arrowDown = faArrowDown;

  stocks: StockListModel[] = [];
  intervalId!:any;

  constructor(private stockService: StockService, private router: Router) {

  }

  ngOnInit() {
    this.stockService.getListedStocks().subscribe( {
      next: data => {
        this.stocks = data;
      },
      error: err => console.log(err),
      complete: () => {
        this.intervalId = setInterval(() => this.getLastStockPrice(), 60000);
      }
    })
  }
  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  sendData(stockId: number | undefined) {
    this.displayStockDetails(stockId);
  }

  displayStockDetails(stockId: number | undefined) {
    this.router.navigate(['stock-details', stockId]);
  }

  setPriceChange(priceChange: number) {
    let result = priceChange.toFixed(2);
    if (priceChange > 0.00) {
      result = '+' + result;
    }
    return result;
  }

  getLastStockPrice() {
    for (let stock of this.stocks) {
      this.stockService.getStockPriceData(stock.ticker).subscribe({
        next: data => stock.stockPriceDetails = data,
        error: err => console.log(err)
      })
    }

  }

  setDecimalsToTwo(lastStockPrice: any) {
    return lastStockPrice.toFixed(2);
  }
}
