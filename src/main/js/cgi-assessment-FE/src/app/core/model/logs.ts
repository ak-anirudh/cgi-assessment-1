export class logs{
  errorDescription: string;
  occurrences: number;

  constructor(errorDescription: string, occurrences: number){
    this.errorDescription = errorDescription;
    this.occurrences = occurrences;
  }
}
