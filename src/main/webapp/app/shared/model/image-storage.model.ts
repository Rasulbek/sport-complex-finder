export interface IImageStorage {
  id?: number;
  imageContentType?: string;
  image?: any;
  sportsHallId?: number;
  isPrimary?: boolean;
}

export class ImageStorage implements IImageStorage {
  constructor(
    public id?: number,
    public imageContentType?: string,
    public image?: any,
    public sportsHallId?: number,
    public isPrimary?: boolean
  ) {
    this.isPrimary = this.isPrimary || false;
  }
}
