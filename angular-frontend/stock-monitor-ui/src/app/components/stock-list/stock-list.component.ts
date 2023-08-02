import { Component } from '@angular/core';
import {StockModel} from 'src/app/models/stock.model';
import { StockService } from 'src/app/services/stock.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent {

  stocks: StockModel[] = [];

  constructor(private stockService: StockService, private router: Router) {

  }

  ngOnInit() {
    this.stockService.getListedStocks().subscribe(response => {
      console.log(response);
      this.stocks = response;
    })
  }

  sendData(stockId: number | undefined, ticker: string) {
    this.displayStockDetails(stockId);
    this.displayStockTicker(ticker);
  }

  displayStockDetails(stockId: number | undefined) {
    this.router.navigate(['stock-details', stockId]);
  }

  displayStockTicker(ticker: string) {
    this.stockService.tickerUpdate.next(ticker);
  }
}
