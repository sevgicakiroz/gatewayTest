import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISchool } from 'app/shared/model/school.model';
import { SchoolService } from './school.service';

@Component({
    selector: 'jhi-school-update',
    templateUrl: './school-update.component.html'
})
export class SchoolUpdateComponent implements OnInit {
    private _school: ISchool;
    isSaving: boolean;

    constructor(private schoolService: SchoolService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ school }) => {
            this.school = school;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.school.id !== undefined) {
            this.subscribeToSaveResponse(this.schoolService.update(this.school));
        } else {
            this.subscribeToSaveResponse(this.schoolService.create(this.school));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISchool>>) {
        result.subscribe((res: HttpResponse<ISchool>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get school() {
        return this._school;
    }

    set school(school: ISchool) {
        this._school = school;
    }
}
