import { Moment } from 'moment';

export interface IKpi {
  id?: number;
  resultTime?: Moment;
  type?: string;
  value?: string;
  factoryName?: string;
  factoryId?: number;
}

export class Kpi implements IKpi {
  constructor(
    public id?: number,
    public resultTime?: Moment,
    public type?: string,
    public value?: string,
    public factoryName?: string,
    public factoryId?: number
  ) {}
}
