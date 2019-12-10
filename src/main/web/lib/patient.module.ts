import { NgModule } from '@angular/core';
import { PatientListComponent } from './components/patient-list.component';
import {
    MatButtonModule,
    MatCardModule, MatChipsModule,
    MatDividerModule,
    MatIconModule,
    MatInputModule, MatListModule, MatProgressBarModule,
    MatSelectModule,
    MatTabsModule
} from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PatientRoutes } from './services/patient.route';
import { PatientDetailsComponent } from './components/patient-details.component';
import { PatientEditComponent } from './components/patient-edit.component';
import { CovalentMessageModule, CovalentSearchModule } from '@covalent/core';
import { JsonFormModule, LamisSharedModule } from '@lamis/web-core';
import { CoreModule } from '@alfresco/adf-core';
import { NgJhipsterModule } from 'ng-jhipster';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
    declarations: [
        PatientListComponent,
        PatientDetailsComponent,
        PatientEditComponent
    ],
    imports: [
        CommonModule,
        NgJhipsterModule,
        LamisSharedModule,
        JsonFormModule,
        MatInputModule,
        MatIconModule,
        MatDividerModule,
        MatCardModule,
        MatSelectModule,
        MatButtonModule,
        MatTabsModule,
        FlexLayoutModule,
        NgxDatatableModule,
        RouterModule.forChild(PatientRoutes),
        MatProgressBarModule,
        CovalentMessageModule,
        MatListModule,
        MatChipsModule,
        CoreModule,
        CovalentSearchModule,
        NgbPaginationModule
    ],
    exports: [
        PatientListComponent,
        PatientDetailsComponent,
        PatientEditComponent
    ],
    providers: []
})
export class PatientModule {
}
