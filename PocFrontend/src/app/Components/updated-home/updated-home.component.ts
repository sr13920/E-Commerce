import { Component ,Inject,OnInit} from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RestApiService } from 'src/app/Service/rest-api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-updated-home',
  templateUrl: './updated-home.component.html',
  styleUrls: ['./updated-home.component.scss']
})
export class UpdatedHomeComponent {
  dataSource!:any;
  cart: any[] = [];
  error:boolean=false;

  constructor(private service:RestApiService,
    public dialog: MatDialog,private router:Router) { }
  
  ngOnInit(): void {
   this.getAllProducts()
   
  }

  viewProfile():void{
    this.router.navigateByUrl("/profile")
  }


getAllProducts():void{
  this.service.getProducts().subscribe((data:any)=>{ 
    this.dataSource=data
    })
}

viewCart():void{
 this.service.setCart(this.cart)
  this.router.navigateByUrl("/orders");
}

addToCart(element:any):void{
  console.log("product added to cart")
  this.cart.push(element);
  
}

}

