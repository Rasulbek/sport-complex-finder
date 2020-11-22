import { FacilityStatus } from 'app/shared/model/enumerations/facility-status.model';

export interface ICategory {
  id?: number;
  name?: string;
  status?: FacilityStatus;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public status?: FacilityStatus) {}
}
