import { IDog } from 'app/shared/model/dog.model';

export interface IRace {
    id?: number;
    name?: string;
    names?: IDog[];
}

export class Race implements IRace {
    constructor(public id?: number, public name?: string, public names?: IDog[]) {}
}
