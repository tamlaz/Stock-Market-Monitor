import { Component } from '@angular/core';
import {StockModel} from "../../models/stock.model";
import {StockService} from "../../services/stock.service";
import {ActivatedRoute} from "@angular/router";
import {StockPrice} from "../../models/stock-price.model";


@Component({
  selector: 'app-stock-details',
  templateUrl: './stock-details.component.html',
  styleUrls: ['./stock-details.component.css']
})
export class StockDetailsComponent {

  stockId?: number;
  ticker?: string;
  stock?: StockModel;
  stockPrice?: StockPrice;

  constructor(private stockService: StockService, private activatedRoute: ActivatedRoute) {
      this.activatedRoute.params.subscribe(params => {
        this.stockId = params['id'];
      })
  }

  ngOnInit(){
      this.stockService.getStockData(this.stockId).subscribe(response => {
        this.stock = response;
        console.log(response);
      })
  }

}
