import { User } from './Users';
import { Project } from './Project';
import {ParentTask} from './parent-task';

export class Task {
    task: string;
    isparent: boolean;
    start_Date:string;
    end_Date:string;
    priority:string;
    endTask:boolean;
    parenTask:string;
    parent_Task:ParentTask;
    project:Project;
    users:User;
}