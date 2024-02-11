import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestApiService } from '../../Service/rest-api.service';
import { user } from './user';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

 user: user ={
  username:'',
  firstname: '',
  lastname:'',
  email:'',
  password:'',
  roles:['user']
};

  message:any;
  error:boolean=false;
  roles = ['admin', 'user'];
  open:boolean=true

  constructor(private service:RestApiService,private router:Router) { }
 
  ngOnInit(): void {
  }

  /* onAreaListControlChanged(list: { selectedOptions: { selected: { value: any; }[]; }; }):void{
    this.user.roles = list.selectedOptions.selected.map((item: { value: any; }) => item.value);
    
  } */

  public registerNow(){
    let resp=this.service.doRegistration(this.user);
    resp.subscribe((data)=>this.message=data);
    this.open=false   
  }

   public login(){
    this.error=false
    this.service.login(this.user.username,this.user.password).subscribe((data:any)=>{
      localStorage.setItem("accessToken",data.token)
      localStorage.setItem("username",this.user.username)
      this.router.navigateByUrl("/updated-home");
    },(err)=>{
      this.error=true;
    });
   }  
}
