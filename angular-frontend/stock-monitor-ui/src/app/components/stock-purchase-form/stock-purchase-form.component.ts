import {Component, Input} from '@angular/core';
import {UserService} from "../../services/user-service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {StockDetailsModel} from "../../models/stock-details-model";

@Component({
  selector: 'app-stock-purchase-form',
  templateUrl: './stock-purchase-form.component.html',
  styleUrls: ['./stock-purchase-form.component.css']
})
export class StockPurchaseFormComponent {

  stockPurchaseForm: FormGroup;
  @Input() currentStock: StockDetailsModel;
  @Input() lastStockPrice: number;

  constructor(private builder: FormBuilder,private userService: UserService) {
    this.stockPurchaseForm = builder.group({
      stockId: [null],
      quantity: [null],
      price: [null],
    })
    this.ngOnInit();
  }

  ngOnInit() {
    this.calculateQuantity();
    this.calculatePrice();
  }

  calculateQuantity() {
    this.stockPurchaseForm.get('price').valueChanges.subscribe({
      next: price => {
        if (price !== null) {
          console.log(this.lastStockPrice);
          this.stockPurchaseForm.get('quantity').setValue(price / this.lastStockPrice, {emitEvent: false});
        } else {
          this.stockPurchaseForm.get('quantity').setValue(0, {emitEvent: false});
        }
      }
    })
  }

  calculatePrice() {
    this.stockPurchaseForm.get('quantity').valueChanges.subscribe({
      next: quantity => {
        if (quantity !== null) {
          this.stockPurchaseForm.get('price').setValue(this.lastStockPrice * quantity, {emitEvent: false});
        } else {
          this.stockPurchaseForm.get('price').setValue(0, {emitEvent: false});
        }
      }
    })
  }


  buyStock() {
    const data = this.stockPurchaseForm.value;
    data.stockId = this.currentStock.id;
    console.log(data);
  }
}
