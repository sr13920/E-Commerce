import { Component } from '@angular/core';
import { MatExpansionPanel } from '@angular/material/expansion';
import { Router } from '@angular/router';
import { RestApiService } from 'src/app/Service/rest-api.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent {

  dataSource: any;
  user: any;
  checkRole: boolean = true;
  selectedRoles: any[] = []
  userToChangeRole = "other";
  roles = ['user', 'admin']
  constructor(private service: RestApiService, private router: Router) {

  }

  ngOnInit(): void {
    this.getAllUsers()
    this.getUser()
  }

  updateRoles(panel:MatExpansionPanel){
    this.service.updateUserRole(this.userToChangeRole, this.selectedRoles).subscribe(data => {
      panel.close();
      this.userToChangeRole = ""
      this.selectedRoles = []
      this.getAllUsers()
    })
  }

  cancelUpdateRoles(panel:MatExpansionPanel) {
    this.userToChangeRole = ""
    this.selectedRoles = []
    panel.close();
  }

  deleteUser(username: any): void {

    this.service.deleteUser(username).subscribe(data => {
      this.getAllUsers()
      if (username == localStorage.getItem(username)) {
        this.router.navigateByUrl('/register')
      }
    })
  }

  editUserRole(data: any): void {
    this.userToChangeRole = data.username
    this.selectedRoles = data.roles
    console.log(this.selectedRoles)

  }

  onAreaListControlChanged(list: { selectedOptions: { selected: { value: any; }[]; }; }): void {
    this.selectedRoles = list.selectedOptions.selected.map((item: { value: any; }) => item.value);
  }


  goBackToProfile(): void {
    this.router.navigateByUrl('/profile')
  }

  getAllUsers(): void {
    this.service.getAllUsers().subscribe(data => {
      this.dataSource = data
    })
  }


  public getUser() {
    this.service.getUser(localStorage.getItem("username")).subscribe((data) => {
      this.user = data
      this.user.roles.forEach((p: any) => {
        if (p == "admin") {
          this.checkRole = false;
        }
      })
    })
  }

}
