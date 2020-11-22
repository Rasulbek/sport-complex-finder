import { ProfileStatus } from 'app/shared/model/enumerations/profile-status.model';

export interface IProfile {
  id?: number;
  phone?: string;
  userName?: string;
  fullName?: string;
  chosenLang?: string;
  status?: ProfileStatus;
  cityName?: string;
  cityId?: number;
}

export class Profile implements IProfile {
  constructor(
    public id?: number,
    public phone?: string,
    public userName?: string,
    public fullName?: string,
    public chosenLang?: string,
    public status?: ProfileStatus,
    public cityName?: string,
    public cityId?: number
  ) {}
}
