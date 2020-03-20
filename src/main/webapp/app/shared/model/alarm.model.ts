import { Moment } from 'moment';

export interface IAlarm {
  id?: number;
  name?: string;
  result?: string;
  cause?: string;
  resultTime?: Moment;
  level?: string;
  status?: string;
  resolveTime?: Moment;
  sourceType?: string;
  sourceId?: string;
  details?: string;
  factoryName?: string;
  factoryId?: number;
}

export class Alarm implements IAlarm {
  constructor(
    public id?: number,
    public name?: string,
    public result?: string,
    public cause?: string,
    public resultTime?: Moment,
    public level?: string,
    public status?: string,
    public resolveTime?: Moment,
    public sourceType?: string,
    public sourceId?: string,
    public details?: string,
    public factoryName?: string,
    public factoryId?: number
  ) {}
}
