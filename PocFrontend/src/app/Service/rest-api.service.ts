import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  public cart = [] as any;
  constructor(private http: HttpClient) { }

  public setCart(cart: any) {
    this.cart = cart
  }

  public getCart() {
    return this.cart
  }

  public updateUser(user: any) {
    return this.http.post("http://localhost:8080/api/updateUser?username=" + localStorage.getItem("username"), user, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public updateUserRole(username: any, roles: any[]) {
    return this.http.post("http://localhost:8080/api/updateUserRole?username=" + username, { roles }, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public getUser(username: any) {
    return this.http.get("http://localhost:8080/api/getUser?username=" + username, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }
    })
  }

  public getAllUsers() {
    return this.http.get("http://localhost:8080/api/getAllUsers", {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }
    })
  }

  public doRegistration(user: any) {
    return this.http.post("http://localhost:8080/api/signup", user);
  }

  public login(username: any, password: any) {
    return this.http.post("http://localhost:8080/api/login", { username, password });
  }

  public getProducts() {
    return this.http.get("http://localhost:8081/api/getProducts", {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }
    })
  }

  public getOrders(username: any) {
    return this.http.get("http://localhost:8082/api/getOrders/" + username, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }
    })
  }

  public updateProduct(id: any, product: any) {
    return this.http.post("http://localhost:8081/api/updateProduct?pid=" + id, { ...product }, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public deleteProduct(id: any) {
    return this.http.delete("http://localhost:8081/api/deleteProduct?pid=" + id, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public deleteUser(username: any) {
    return this.http.delete("http://localhost:8080/api/deleteUser?username=" + username, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }


  public addProduct(product: any) {
    return this.http.post("http://localhost:8081/api/addProduct", { ...product }, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public placeOrder(cart: any) {
    return this.http.post("http://localhost:8082/api/placeOrder", { products: cart }, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public updateOrder(oid: any, products: any) {
    return this.http.post("http://localhost:8082/api/updateOrder", { oid, products }, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

  public deleteOrder(oid: any) {
    return this.http.delete("http://localhost:8082/api/deleteOrder/" + oid, {
      "headers": {
        "authorization": `Bearer ${localStorage.getItem("accessToken")}`
      }, "responseType": "text"
    })
  }

}
