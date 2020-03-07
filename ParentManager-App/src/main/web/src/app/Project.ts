import { User } from './Users';

export class Project {
    project: string;
    start_Date:string;
    end_Date:string;
    priority:string;
    projectstatus:boolean;
    taskcount:BigInteger;
    taskcompletion:BigInteger;
    user:User;
}