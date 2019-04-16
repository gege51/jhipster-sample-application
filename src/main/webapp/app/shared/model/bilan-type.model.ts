import { IBilanCheck } from 'app/shared/model/bilan-check.model';

export interface IBilanType {
    id?: number;
    type?: string;
    name?: string;
    bilanChecks?: IBilanCheck[];
}

export class BilanType implements IBilanType {
    constructor(public id?: number, public type?: string, public name?: string, public bilanChecks?: IBilanCheck[]) {}
}
