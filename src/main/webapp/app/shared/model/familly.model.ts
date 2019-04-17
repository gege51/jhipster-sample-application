import { IDog } from 'app/shared/model/dog.model';
import { ILocation } from 'app/shared/model/location.model';

export interface IFamilly {
    id?: number;
    name?: string;
    name?: IDog;
    locations?: ILocation[];
}

export class Familly implements IFamilly {
    constructor(public id?: number, public name?: string, public name?: IDog, public locations?: ILocation[]) {}
}
