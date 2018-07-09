export const enum Status {
    ACTIVE = 'ACTIVE',
    PASSIVE = 'PASSIVE'
}

export interface ISchool {
    id?: number;
    name?: string;
    description?: string;
    foundedYear?: number;
    principal?: string;
    vicePrincipal?: string;
    studentCount?: number;
    employeeCount?: number;
    address?: string;
    status?: Status;
}

export class School implements ISchool {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public foundedYear?: number,
        public principal?: string,
        public vicePrincipal?: string,
        public studentCount?: number,
        public employeeCount?: number,
        public address?: string,
        public status?: Status
    ) {}
}
