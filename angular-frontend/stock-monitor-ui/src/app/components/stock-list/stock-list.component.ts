import { Component } from '@angular/core';


import {Router} from "@angular/router";
import {faArrowDown, faArrowUp} from "@fortawesome/free-solid-svg-icons";
import {UserAccountService} from "../../services/user-account.service";
import {StockListItemModel} from "../../models/stock-list-item-model";
import {StockService} from "../../services/stock.service";

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent {

  stocks: StockListItemModel[] = [];
  intervalId!:any;
  currentUsersWatchList: number[] = [];


  constructor(private stockService: StockService,
              private router: Router,
              private userService: UserAccountService) {

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
    this.reLoadWatchList();
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

  handleWatchList(stockId: number) {
    if (this.checkIfStockPartOfWatchList(stockId)) {
      this.removeFromWatchList(stockId);
    } else {
      this.addToWatchList(stockId);
    }
  }

  removeFromWatchList(stockId: number) {
    this.userService.removeFromWatchList(stockId).subscribe({
      error: err => {
        console.log(err);
      },
      complete: () => {
        this.reLoadWatchList();
      }
    })
  }

  addToWatchList(stockId: number) {
    this.userService.addToWatchList(stockId).subscribe({
      error: err => {
        console.log(err);
      },
      complete: () => {
        this.reLoadWatchList();
      }
    })
  }

  reLoadWatchList() {
    if (sessionStorage.getItem('user')) {
      this.userService.getUserProfileDetails().subscribe({
        next: data => this.currentUsersWatchList = data.watchList,
        complete: () => console.log(this.currentUsersWatchList)
      })
    }
  }

  checkIfStockPartOfWatchList(id:number) {
    if (this.currentUsersWatchList.length === 0) {
      return false;
    }
    return this.currentUsersWatchList.includes(id);
  }
}
