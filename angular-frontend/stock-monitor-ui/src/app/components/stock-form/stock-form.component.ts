import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StockService} from "../../services/stock.service";

@Component({
  selector: 'app-stock-form',
  templateUrl: './stock-form.component.html',
  styleUrls: ['./stock-form.component.css']
})
export class StockFormComponent {

  stockForm: FormGroup;
  isStockAdded: boolean = false;

  constructor(private builder: FormBuilder,
              private stockService: StockService) {
    this.stockForm = builder.group({
      ticker: ['', Validators.required]
    })
  }


  addStock() {
    const data = this.stockForm.value;
    this.stockService.addStock(data.ticker).subscribe({
      next: () => {
        this.stockForm.reset();
      },
      error: () => {

      },
      complete: () => {
        this.isStockAdded = true;
        setTimeout(() => {
          this.isStockAdded = false;
        }, 5000);
      }
    })
  }
}
