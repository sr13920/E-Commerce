import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { RestApiService } from 'src/app/Service/rest-api.service';
import SwiperCore, { Navigation } from 'swiper';

SwiperCore.use([Navigation])
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent {

  user: any;
  dataSource: any;
  products: any[] = [];


  constructor(private service: RestApiService,
    public dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.getUser()
    this.getAllProducts()

  }

  getFilteredProducts(data: any) {
    data.forEach((p: any) => {
      if (p.seller.username == localStorage.getItem("username")) {
        this.products.push(p)
      }
    })

    return this.products
  }

  getAllProducts(): void {
    this.service.getProducts().subscribe((data: any) => {
      this.dataSource = this.getFilteredProducts(data);
    })
  }

  addProduct(): void {
    this.dialog.open(updatedAddEditProductDialog, {
      data: { productname: "", price: "", category: "FASHION" }
    }).afterClosed().subscribe((data) => {
      if (data) {
        this.service.addProduct(data).subscribe((data1) => {
          this.products=[]
          this.getAllProducts()
        }, error => {
          console.log(error)
        })
      }
    });
  }

  editProduct(element: any): void {
    this.dialog.open(updatedAddEditProductDialog, {
      data: element
    }).afterClosed().subscribe((data) => {
      if (data) {
        this.service.updateProduct(data.pid, data).subscribe((data1) => {
          this.products=[]
          this.getAllProducts()
        }, error => {
          console.log(error)
        })
      }
    });
  }

  deleteProduct(element: any): void {
    this.service.deleteProduct(element.pid).subscribe((data) => {
      this.products=[]
      this.getAllProducts()
    }, error => {
      console.log(error)
    })
  }

  editUser(): void {
    this.dialog.open(updatedUserDialog, {
      data: this.user
    }).afterClosed().subscribe((data) => {
      if (data) {
        var user = { firstname: data.firstname, lastname: data.lastname, email: data.email }
        this.service.updateUser(user).subscribe((data1) => {
          this.getUser()
        }, error => {
          console.log(error)
        })
      }
    });
  }

  public showUsers() {
    this.router.navigateByUrl('/users');
  }

  onSwiper([swiper]: any) {
    console.log(swiper);
  }
  onSlideChange() {
    console.log('slide change');
  }

  public goBackToHome() {
    this.router.navigateByUrl('/updated-home')
  }

  public logout() {
    localStorage.removeItem("accessToken")
    this.router.navigateByUrl("/register")
  }

  public getUser() {
    this.service.getUser(localStorage.getItem("username")).subscribe((data) => {
      this.user = data
    })
  }

}


@Component({
  selector: 'edit-user',
  templateUrl: 'editUser.html',
})

export class updatedUserDialog {

  userData: any;
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<updatedUserDialog>) {
    this.userData = { ...data }
  }

  addEdit(): void {
    this.dialogRef.close(this.userData);
  }
}


@Component({
  selector: 'product-add-edit',
  templateUrl: 'productAddEdit.html',
})

export class updatedAddEditProductDialog {

  productData: any;
  category = [{ id: "FASHION", name: "Fashion" }, { id: "ELECTRONICS", name: "Electronics" }, { id: "BOOKS", name: "Books" }];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public dialogRef: MatDialogRef<updatedAddEditProductDialog>) {
    this.productData = { ...data }
  }

  addEdit(): void {
    this.productData.price = parseInt(this.productData.price);
    this.dialogRef.close(this.productData);
  }
}



