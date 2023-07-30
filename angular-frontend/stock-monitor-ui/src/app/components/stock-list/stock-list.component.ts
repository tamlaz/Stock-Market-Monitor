import { Component } from '@angular/core';
import { Stock } from 'src/app/models/stock.model';
import { StockService } from 'src/app/services/stock.service';

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent {

  stocks: Stock[] = [];

  constructor(private stockService: StockService) {

  }

  ngOnInit() {
    this.stockService.getListedStocks().subscribe(response => {
      console.log(response);
      this.stocks = response;
    })
  }

}
