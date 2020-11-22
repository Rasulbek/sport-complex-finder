import { FacilityStatus } from 'app/shared/model/enumerations/facility-status.model';

export interface ISportsHall {
  id?: number;
  name?: string;
  description?: string;
  contactPerson?: string;
  phone?: string;
  telegramNick?: string;
  price?: number;
  status?: FacilityStatus;
  latitude?: number;
  longitude?: number;
  address?: string;
  landmark?: string;
  ownerTelegramId?: string;
  categoryName?: string;
  categoryId?: number;
  cityName?: string;
  cityId?: number;
}

export class SportsHall implements ISportsHall {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public contactPerson?: string,
    public phone?: string,
    public telegramNick?: string,
    public price?: number,
    public status?: FacilityStatus,
    public latitude?: number,
    public longitude?: number,
    public address?: string,
    public landmark?: string,
    public ownerTelegramId?: string,
    public categoryName?: string,
    public categoryId?: number,
    public cityName?: string,
    public cityId?: number
  ) {}
}
