import { Moment } from 'moment';
import { IBilanType } from 'app/shared/model/bilan-type.model';
import { ICriteria } from 'app/shared/model/criteria.model';

export interface IBilanCheck {
    id?: number;
    date?: Moment;
    bilanTypes?: IBilanType[];
    criteria?: ICriteria[];
}

export class BilanCheck implements IBilanCheck {
    constructor(public id?: number, public date?: Moment, public bilanTypes?: IBilanType[], public criteria?: ICriteria[]) {}
}
