import { Moment } from 'moment';
import { IDemand } from 'app/shared/model/demand.model';

export interface IOrder {
  id?: number;
  orderId?: string;
  customer?: string;
  beginTime?: Moment;
  deadLine?: Moment;
  status?: string;
  details?: string;
  idId?: number;
  demands?: IDemand[];
  factoryName?: string;
  factoryId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderId?: string,
    public customer?: string,
    public beginTime?: Moment,
    public deadLine?: Moment,
    public status?: string,
    public details?: string,
    public idId?: number,
    public demands?: IDemand[],
    public factoryName?: string,
    public factoryId?: number
  ) {}
}
