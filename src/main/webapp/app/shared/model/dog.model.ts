import { Moment } from 'moment';
import { ICenter } from 'app/shared/model/center.model';
import { IBreeding } from 'app/shared/model/breeding.model';
import { IFamilly } from 'app/shared/model/familly.model';
import { IWeight } from 'app/shared/model/weight.model';
import { IHealthSheet } from 'app/shared/model/health-sheet.model';
import { IBilanCheck } from 'app/shared/model/bilan-check.model';
import { IRace } from 'app/shared/model/race.model';
import { IColor } from 'app/shared/model/color.model';

export interface IDog {
    id?: number;
    puceId?: number;
    tatooId?: string;
    passportId?: number;
    name?: string;
    name1?: string;
    birthday?: Moment;
    sexe?: string;
    hcNum?: string;
    centerNames?: ICenter[];
    breedingNames?: IBreeding[];
    famillyNames?: IFamilly[];
    weights?: IWeight[];
    healthSheets?: IHealthSheet[];
    dateBilans?: IBilanCheck[];
    raceName?: IRace;
    colorName?: IColor;
}

export class Dog implements IDog {
    constructor(
        public id?: number,
        public puceId?: number,
        public tatooId?: string,
        public passportId?: number,
        public name?: string,
        public name1?: string,
        public birthday?: Moment,
        public sexe?: string,
        public hcNum?: string,
        public centerNames?: ICenter[],
        public breedingNames?: IBreeding[],
        public famillyNames?: IFamilly[],
        public weights?: IWeight[],
        public healthSheets?: IHealthSheet[],
        public dateBilans?: IBilanCheck[],
        public raceName?: IRace,
        public colorName?: IColor
    ) {}
}
