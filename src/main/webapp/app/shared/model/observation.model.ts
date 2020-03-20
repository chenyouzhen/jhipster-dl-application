import { Moment } from 'moment';

export interface IObservation {
  id?: number;
  phenomenonTime?: Moment;
  result?: string;
  resultTime?: Moment;
  property?: string;
  unit?: string;
  assemblylineName?: string;
  assemblylineId?: number;
}

export class Observation implements IObservation {
  constructor(
    public id?: number,
    public phenomenonTime?: Moment,
    public result?: string,
    public resultTime?: Moment,
    public property?: string,
    public unit?: string,
    public assemblylineName?: string,
    public assemblylineId?: number
  ) {}
}
