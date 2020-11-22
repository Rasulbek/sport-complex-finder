import { AdminRole } from 'app/shared/model/enumerations/admin-role.model';

export interface IAdministrator {
  id?: number;
  chatId?: string;
  role?: AdminRole;
}

export class Administrator implements IAdministrator {
  constructor(public id?: number, public chatId?: string, public role?: AdminRole) {}
}
