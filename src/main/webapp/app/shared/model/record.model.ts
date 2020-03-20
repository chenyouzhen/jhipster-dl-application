import { Moment } from 'moment';

export interface IRecord {
  id?: number;
  dailyOutput?: number;
  resultTime?: Moment;
  productName?: string;
  productId?: number;
}

export class Record implements IRecord {
  constructor(
    public id?: number,
    public dailyOutput?: number,
    public resultTime?: Moment,
    public productName?: string,
    public productId?: number
  ) {}
}
