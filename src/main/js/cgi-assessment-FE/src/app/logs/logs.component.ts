import {Component, OnInit} from '@angular/core';
import {LogsService} from '../core/services/logs.service';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})

export class LogsComponent implements OnInit {

  logLevel: string[] = ['DEBUG', 'ERROR', 'INFO', 'WARN'];
  level: any;
  topN: any;
  displayedColumns: string[] = ['errorDescription', 'occurrences'];

  logResult = new MatTableDataSource();

  constructor(private service: LogsService) {
    this.level = 'DEBUG';
    this.topN = 0;
  }

  ngOnInit(): void {
   this.getLogs();
  }

  getLogs(): void {
    this.searchforLogs(this.level, this.topN);
  }

  searchforLogs(level: string, num: number): void {
    this.service.findByLevelAndLimit(this.level, this.topN).subscribe(data => {
      this.logResult.data = data;
    });
  }
}
