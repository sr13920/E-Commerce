import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RestApiService } from 'src/app/Service/rest-api.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})
export class OrdersComponent {
  dataSource: any;
  productData:any;
  orderToUpdate:any;
selectedProducts:any=[];
error:boolean=false;
unplacedOrder={products:[],oid:"new-id"} as any;

  constructor(private service:RestApiService,private router:Router) { }
 
  ngOnInit(): void {
    this.unplacedOrder.products=this.service.getCart()
    this.getAllOrders()
  }


onAreaListControlChanged(list: { selectedOptions: { selected: { value: any; }[]; }; }):void{
  this.selectedProducts = list.selectedOptions.selected.map((item: { value: any; }) => item.value);
}

goBackToHome(){
  this.router.navigateByUrl('/updated-home');
}

getAllOrders():void{
  this.service.getOrders(localStorage.getItem("username")).subscribe((data:any)=>{ 
    this.dataSource=data
    if(this.unplacedOrder.oid && this.unplacedOrder?.products?.length)
      this.dataSource.push(this.unplacedOrder)
    this.getAllProducts()
    })
}

getAllProducts():void{
  this.service.getProducts().subscribe((data:any)=>{ 
    this.productData=data 
  })
}

updateOrder():void{
  var updatedProducts=this.productData.filter((p: { pid: any; })=>this.selectedProducts.includes(p.pid))
  if(this.orderToUpdate!="new-id"){
    this.service.updateOrder(this.orderToUpdate,updatedProducts).subscribe((data)=>{
      this.getAllOrders()
      this.orderToUpdate=""
      this.selectedProducts=[]
    })
  }else{
    this.unplacedOrder.products=updatedProducts
    this.orderToUpdate=""
    this.selectedProducts=[]
  }
}

placeOrder():void{
  this.error=false
 this.service.placeOrder(this.unplacedOrder.products).subscribe((data:any)=>{
  this.unplacedOrder={}
  this.getAllOrders()
  this.service.setCart([])
 },(err)=>{
   this.error=true;
 });
}

cancelUpdate():void{
  this.orderToUpdate=""
  this.selectedProducts=[]
}

editOrder(data:any):void{
  this.orderToUpdate=data.oid
  this.selectedProducts=data.products.map((p: { pid: any; })=>p.pid)
  console.log(this.selectedProducts)

}

deleteOrder(oid:any):void{
  this.service.deleteOrder(oid).subscribe((data)=>{
    console.log(data)
    this.getAllOrders()
  })
}

getTotalAmount(data:any){
  var amount=0;
  data.products.forEach((element: { price: number; }) => {
    amount+=element.price;
  });
  return amount;
}
}
