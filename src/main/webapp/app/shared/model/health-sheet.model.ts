import { Moment } from 'moment';
import { IDog } from 'app/shared/model/dog.model';

export interface IHealthSheet {
    id?: number;
    date?: Moment;
    reason?: string;
    comment?: string;
    dog?: IDog;
}

export class HealthSheet implements IHealthSheet {
    constructor(public id?: number, public date?: Moment, public reason?: string, public comment?: string, public dog?: IDog) {}
}
